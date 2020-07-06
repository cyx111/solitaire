package GUI;

import Card.CardImages;
import Card.GameModel;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/*���ƶ�
 ��ʾ��岢�����������
 �����������Ƶġ�������Ϸģ��״̬�ı仯
 ������ǿյģ������Զ���������ʧ��*/
class DeckPileView extends HBox implements GameModelListener {
	private static final String BUTTON_STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
	private static final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
	Image imageEmpty = new Image(CardImages.class.getResourceAsStream("../resources/kong.jpg"));

	DeckPileView() {
		final Button button = new Button();
		button.setGraphic(new ImageView(CardImages.getBack()));
		button.setStyle(BUTTON_STYLE_NORMAL);
		button.setOnMousePressed(new EventHandler<MouseEvent>() {// �������
			@Override
			public void handle(MouseEvent pEvent) {
				((Button) pEvent.getSource()).setStyle(BUTTON_STYLE_PRESSED);
			}
		});

		button.setOnMouseReleased(new EventHandler<MouseEvent>() {// �ɿ����
			@Override
			public void handle(MouseEvent pEvent) {
				((Button) pEvent.getSource()).setStyle(BUTTON_STYLE_NORMAL);
				if (GameModel.instance().getStack(0).isEmpty()) {
					if (GameModel.instance().getStack(1).isEmpty()) {// ���ƶѺ����ƶѶ�Ϊ��
						GameModel.instance().reset_all();
					} else {// ���ƶ�Ϊ�� ���ƶѲ�Ϊ��
						GameModel.instance().reset();// ���ƶ������ƷŻط��ƶ�
					}
				} else {// ���ƶѲ�Ϊ��
					GameModel.instance().Desc_to_DisCard();// ��һ����
				}
			}
		});
		getChildren().add(button);
		GameModel.instance().addListener(this);// ����ƶ�����
	}

	@Override
	public void gameStateChanged() {
		if (GameModel.instance().getStack(0).isEmpty()) {// ���ƶ�Ϊ�� ��ť��ʾ��
			((Button) getChildren().get(0)).setGraphic(new ImageView(imageEmpty));
		} else {// ���ƶѲ�Ϊ�� ��ťͼ��Ϊ�Ʊ���
			((Button) getChildren().get(0)).setGraphic(new ImageView(CardImages.getBack()));
		}
	}
}
