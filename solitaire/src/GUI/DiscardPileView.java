package GUI;

import Card.Card;
import Card.CardImages;
import Card.GameModel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;

class DiscardPileView extends StackPane implements GameModelListener {
	private static final int PADDING = 5;
	private int aIndex;

	DiscardPileView() {
		this.aIndex = 1;
		setPadding(new Insets(PADDING));
		setAlignment(Pos.TOP_CENTER);
		final ImageView image = new ImageView(CardImages.getBack());
		image.setVisible(false);
		getChildren().add(image);
		buildLayout();
		GameModel.instance().addListener(this);
	}

	private void buildLayout() {
		getChildren().clear();
		if (GameModel.instance().getStack(1).size() > 0) {
				Card cardView = GameModel.instance().getStack(1).peek();
				final ImageView image = new ImageView(CardImages.getCard(cardView));
				getChildren().add(image);//��ʾ������һ����
				setOnDragOver(createDragOverHandler(image, cardView));// �����϶���Ŀ���Ϸ���ʱ�򣬻᲻ͣ��ִ�С�
				setOnDragEntered(createDragEnteredHandler(image, cardView));// �����϶���Ŀ��ؼ���ʱ�򣬻�ִ������¼��ص���
				setOnDragExited(createDragExitedHandler(image, cardView));// �����϶��Ƴ�Ŀ��ؼ���ʱ��ִ�����������
				setOnDragDropped(createDragDroppedHandler(image, cardView));// �����϶���Ŀ�겢�ɿ�����ʱ��ִ�����DragDropped��
				image.setOnDragDetected(createDragDetectedHandler(image, cardView));// �����һ��Node�Ͻ����϶���ʱ�򣬻��⵽�϶�����������ִ�����EventHandler
		}
	}

	private EventHandler<MouseEvent> createDragDetectedHandler(final ImageView pImageView, final Card pCard) {
		// �����һ��Node�Ͻ����϶���ʱ�򣬻��⵽�϶�����������ִ�����EventHandler��
		return new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent pMouseEvent) {
				Dragboard db = pImageView.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString(pCard.getIDString());
				db.setContent(content);
				pMouseEvent.consume();
				GameModel.instance().setFromIndex(1);
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
				if (db.hasString()) {
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

	@Override
	public void gameStateChanged() {
		buildLayout();
	}

}
