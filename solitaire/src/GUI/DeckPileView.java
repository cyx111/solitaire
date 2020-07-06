package GUI;

import Card.CardImages;
import Card.GameModel;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/*发牌堆
 显示面板并允许单击的组件
 它是用来抽牌的。倾听游戏模型状态的变化
 如果它是空的，它会自动更新以消失。*/
class DeckPileView extends HBox implements GameModelListener {
	private static final String BUTTON_STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
	private static final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
	Image imageEmpty = new Image(CardImages.class.getResourceAsStream("../resources/kong.jpg"));

	DeckPileView() {
		final Button button = new Button();
		button.setGraphic(new ImageView(CardImages.getBack()));
		button.setStyle(BUTTON_STYLE_NORMAL);
		button.setOnMousePressed(new EventHandler<MouseEvent>() {// 按下鼠标
			@Override
			public void handle(MouseEvent pEvent) {
				((Button) pEvent.getSource()).setStyle(BUTTON_STYLE_PRESSED);
			}
		});

		button.setOnMouseReleased(new EventHandler<MouseEvent>() {// 松开鼠标
			@Override
			public void handle(MouseEvent pEvent) {
				((Button) pEvent.getSource()).setStyle(BUTTON_STYLE_NORMAL);
				if (GameModel.instance().getStack(0).isEmpty()) {
					if (GameModel.instance().getStack(1).isEmpty()) {// 发牌堆和弃牌堆都为空
						GameModel.instance().reset_all();
					} else {// 发牌堆为空 弃牌堆不为空
						GameModel.instance().reset();// 弃牌堆所有牌放回发牌堆
					}
				} else {// 发牌堆不为空
					GameModel.instance().Desc_to_DisCard();// 发一张牌
				}
			}
		});
		getChildren().add(button);
		GameModel.instance().addListener(this);// 添进牌堆数组
	}

	@Override
	public void gameStateChanged() {
		if (GameModel.instance().getStack(0).isEmpty()) {// 发牌堆为空 按钮显示空
			((Button) getChildren().get(0)).setGraphic(new ImageView(imageEmpty));
		} else {// 发牌堆不为空 按钮图形为牌背面
			((Button) getChildren().get(0)).setGraphic(new ImageView(CardImages.getBack()));
		}
	}
}
