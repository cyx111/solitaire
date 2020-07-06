package GUI;

import Card.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

public class TablePileView extends StackPane implements GameModelListener {
	private static final int PADDING = 5;
	private static final int Y_OFFSET = 30;
	private int aIndex;
	private static final String BORDER_STYLE = "-fx-border-color: lightgray;" + "-fx-border-width: 2.8;"
			+ " -fx-border-radius: 10.0";

	TablePileView(TablePile pIndex) {
		aIndex = pIndex.ordinal() + 2;// ����ö��������� 2-8
		setPadding(new Insets(PADDING));
		setStyle(BORDER_STYLE);
		setAlignment(Pos.TOP_CENTER);
		final ImageView image = new ImageView(CardImages.getBack());
		image.setVisible(false);
		getChildren().add(image);
		buildLayout();
		GameModel.instance().addListener(this);
	}

	private void buildLayout() {
		getChildren().clear();
		TableStack stack = (TableStack) GameModel.instance().getStack(aIndex);
		if (stack.isEmpty()) {
			ImageView image = new ImageView(CardImages.getBack());
			image.setVisible(false);
			getChildren().add(image);
			return;
		}
		for (int i = 0; i < stack.size(); i++) {
			Card cardView = stack.peek(i);
			if (cardView.isFaceUp() == true) {
				final ImageView image = new ImageView(CardImages.getCard(cardView));
				image.setTranslateY(Y_OFFSET * i);
				getChildren().add(image);
				setOnDragOver(createDragOverHandler(image, cardView));// �����϶���Ŀ���Ϸ���ʱ�򣬻᲻ͣ��ִ�С�
				setOnDragEntered(createDragEnteredHandler(image, cardView));// �����϶���Ŀ��ؼ���ʱ�򣬻�ִ������¼��ص���
				setOnDragExited(createDragExitedHandler(image, cardView));// �����϶��Ƴ�Ŀ��ؼ���ʱ��ִ�����������
				setOnDragDropped(createDragDroppedHandler(image, cardView));// �����϶���Ŀ�겢�ɿ�����ʱ��ִ�����DragDropped�¼���
				image.setOnDragDetected(createDragDetectedHandler(image, cardView));// �����һ��Node�Ͻ����϶���ʱ�򣬻��⵽�϶�����������ִ�����EventHandler��
			} else {
				final ImageView image = new ImageView(CardImages.getBack());
				image.setTranslateY(Y_OFFSET * i);
				getChildren().add(image);
			}
		}
	}

	private EventHandler<MouseEvent> createDragDetectedHandler(final ImageView pImageView, final Card pCard) {
		// �����һ��Node�Ͻ����϶���ʱ�򣬻��⵽�϶�����������ִ�����EventHandler��
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent pMouseEvent) {
				Dragboard db = pImageView.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString(GameModel.instance().serialize(pCard, aIndex));
				db.setContent(content);
				pMouseEvent.consume();
				GameModel.instance().setFromIndex(aIndex);
			}
		};
	}

	private EventHandler<DragEvent> createDragOverHandler(final ImageView pImageView, final Card pCard) {
		// �����϶���Ŀ���Ϸ���ʱ�򣬻᲻ͣ��ִ�С�
		return new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent pEvent) {
				if (pEvent.getGestureSource() != pImageView && pEvent.getDragboard().hasString()) {
					if (GameModel.instance().isLegalMove(GameModel.instance().getTop(pEvent.getDragboard().getString()),aIndex)) {
						pEvent.acceptTransferModes(TransferMode.MOVE);
					}
				}
				pEvent.consume();
			}
		};
	}

	private EventHandler<DragEvent> createDragEnteredHandler(final ImageView pImageView, final Card pCard) {
		// �����϶���Ŀ��ؼ���ʱ�򣬻�ִ������¼��ص���
		return new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent pEvent) {
				if (GameModel.instance().isLegalMove(GameModel.instance().getTop(pEvent.getDragboard().getString()),aIndex)) {
					pImageView.setEffect(new DropShadow());
				}
				pEvent.consume();
			}
		};
	}

	private EventHandler<DragEvent> createDragExitedHandler(final ImageView pImageView, final Card pCard) {
		// �����϶��Ƴ�Ŀ��ؼ���ʱ��ִ�����������
		return new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent pEvent) {
				pImageView.setEffect(null);
				pEvent.consume();
			}
		};
	}

	private EventHandler<DragEvent> createDragDroppedHandler(final ImageView pImageView, final Card pCard) {
		// �����϶���Ŀ�겢�ɿ�����ʱ��ִ�����DragDropped�¼���
		return new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent pEvent) {
				Dragboard db = pEvent.getDragboard();
				boolean success = false;
				if (db.hasString()) {//�ƶ�����
					GameModel.instance().moveCard(GameModel.instance().StringToStack(db.getString()),aIndex);
					success = true;
					ClipboardContent content = new ClipboardContent();
					content.putString(null);
					db.setContent(content);
				}
				pEvent.setDropCompleted(success);
				pEvent.consume();
			}
		};
	}

	public void gameStateChanged() {
		buildLayout();
	}
}