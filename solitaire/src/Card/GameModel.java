package Card;

import GUI.GameModelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class GameModel {
	private static final GameModel INSTANCE = new GameModel();
	private ArrayList<CardStack> card_Stacks;// ��������ϵ������ƶ�
	private int fromIndex;
	private DeckStack deck_Stack;// 0���ƶ�
	private DiscardStack disCard_Stack;// 1
	private TableStack[] table_Stacks;// 2-8
	private SuitStack[] suit_Stacks;// 9-12

	

	private static final String SEPARATOR = ";";// �����ַ����ָ�ķ���

	private final List<GameModelListener> aListeners = new ArrayList<GameModelListener>();
	// ���GameModelListener���ArrayList����Ϊ�����ƶѵļ�������ʵ�ִ��������ĸ���

	public GameModel() {
		init();
	}

	public void init() {// ��ʼ��ʮ�����ƶѣ�������Ѻ����ƶ�������ơ�������ÿһ���ƶѰ�˳�����ArrayList�У�֮�������ŷ��ʲ�ͬ���ƶ�
		//this.regret_stack = new ArrayList<CardStack>();
		this.table_Stacks = new TableStack[7];
		for (int i = 0; i < table_Stacks.length; i++) {
			this.table_Stacks[i] = new TableStack();
		}
		this.deck_Stack = new DeckStack();
		this.disCard_Stack = new DiscardStack();
		this.suit_Stacks = new SuitStack[4];
		for (int i = 0; i < suit_Stacks.length; i++) {
			this.suit_Stacks[i] = new SuitStack();
		}

		this.card_Stacks = new ArrayList<CardStack>();
		this.card_Stacks.add(this.deck_Stack);// 0
		this.card_Stacks.add(this.disCard_Stack);// 1
		for (int i = 0; i < table_Stacks.length; i++) {
			this.card_Stacks.add(this.table_Stacks[i]);// 2-8
		}
		for (int i = 0; i < suit_Stacks.length; i++) {
			this.card_Stacks.add(this.suit_Stacks[i]);// 9-12
		}

		Random random = new Random();

		ArrayList<Card> normal_Rank = new ArrayList<Card>();
		Card temp_card = null;
		// ʹ��normal_Rank�ݴ�52�ſ��Ƶ���Ϣ
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				temp_card = new Card(rank, suit);
				normal_Rank.add(temp_card);
			}
		}

		int i;
		// ������߸����ƶѵ���
		int index = 0;
		for (i = 0; i < 7; ++i) {
			for (int j = 0; j <= i; ++j) {
				while (true) {
					index = random.nextInt(normal_Rank.size());
					temp_card = (Card) normal_Rank.get(index);
					if (!this.table_Stacks[i].isEmpty() && temp_card.isRed() != this.table_Stacks[i].peek().isRed()) {
						this.table_Stacks[i].init(temp_card);
						normal_Rank.remove(index);
						break;
					}
					if (this.table_Stacks[i].isEmpty()) {
						this.table_Stacks[i].init(temp_card);
						normal_Rank.remove(index);
						break;
					}
				}
			}
		}
		// ��ÿ���ƶѵĶ��˷���
		for (i = 0; i < 7; ++i) {
			this.table_Stacks[i].peek().setFaceUp(true);
		}
		// ��ʣ���Ʒ��뷢�ƶ�
		for (i = 0; i < 24; ++i) {
			index = random.nextInt(normal_Rank.size());
			temp_card = (Card) normal_Rank.get(index);
			this.deck_Stack.init(temp_card);
			normal_Rank.remove(index);
		}
	}

	// ��ȡ�ƶ�//���Ը�����Ŵ�ArrayList��ȡ���Լ���Ҫ���ƶѣ�Ȼ�󷵻ء�ʵ�����ڲ����ݵı�����
	public CardStack getStack(int index) {
		return this.card_Stacks.get(index);
	}

	// ��ȡʵ��
	public static GameModel instance() {
		return INSTANCE;
	}

	// ���ÿ����ƶ��ļ�����
	public void addListener(GameModelListener pListener) {
		if (pListener != null)
			;
		aListeners.add(pListener);
	}

	// �ڿ����ƶ��󣬸�������
	private void notifyListeners() {
		for (GameModelListener listener : aListeners) {
			listener.gameStateChanged();//ÿ��ʵ�ֽӿڵ������еķ���
		}
	}
	// ���ؿ��Ƶ������У��������ѵ��߸��ƶѵ������С����϶�ʱ�����϶�һ�ѿ��ơ�
	public CardStack getSubStack(Card pCard, int aIndex) {
		TableStack stack = (TableStack) GameModel.instance().getStack(aIndex);
		TableStack temp_stack = stack.getSubStack(pCard, stack);
		return temp_stack;
	}

	// �ж� �ƶ��Ƿ�Ϸ�
	public boolean isLegalMove(Card pCard, int aIndex) {
		if (aIndex >= 1 && aIndex <= 8) {
			return canMoveToTableStack(pCard, aIndex);
		} else if (aIndex >= 9 && aIndex <= 12) {
			return canMoveToSuitStack(pCard, aIndex);
		}
		return false;
	}

	// �Ƿ����Ƶ������
	public boolean canMoveToTableStack(Card pCard, int aIndex) {
		if (pCard != null) {
			CardStack temp_stack = getStack(aIndex);
			if (temp_stack.isEmpty()) {
				return true;
			} else {
				return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal() - 1
						&& pCard.isRed() != temp_stack.peek().isRed();
			}
		}
		return false;
	}

	// �Ƿ����Ƶ���ɫ�Ѷ�
	public boolean canMoveToSuitStack(Card pCard, int aIndex) {
		assert pCard != null;
		CardStack temp_stack = getStack(aIndex);
		if (temp_stack.isEmpty()) {
			return pCard.getRank() == Rank.ACE;
		} else {
			return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal() + 1
					&& pCard.getSuit() == temp_stack.peek().getSuit();
		}

	}

	public void Desc_to_DisCard() {//���ƣ�ÿ�ε�����ư�ť����һ���ƴӷ��ƶѼ��붪����
		Card temp_card = this.getStack(0).peek();
		this.getStack(1).init(temp_card);
		this.getStack(0).pop();
		notifyListeners();
	}

	// �ƶ��ƶ�
	public boolean moveCard(CardStack from, int aIndex) {
		if (aIndex >= 2 && aIndex <= 8) {
			TableStack to = (TableStack) this.getStack(aIndex);
			if (!to.isEmpty()) {
				this.getStack(aIndex).push(from);
				pop_from(from);
				notifyListeners();
				return true;
			} else if (from.isEmpty()) {
				return false;
			} else {
				this.getStack(aIndex).push(from);
				pop_from(from);
				notifyListeners();
			}
		} else if (aIndex >= 9 && aIndex <= 12) {
			SuitStack to = (SuitStack) this.getStack(aIndex);
			if (to.isEmpty()) {// ��ȡ�����¸�һ����
				this.getStack(aIndex).push(from);
				pop_from(from);
				notifyListeners();
				return true;
			} else {
				this.getStack(aIndex).push(from);
				pop_from(from);
				notifyListeners();
			}
		}
		return true;
	}

	public void pop_from(CardStack to) {//��һ�鿨�ƴ���ʼ�ƶ�ɾ��
		for (int j = 0; j < to.size(); j++) {
			this.getStack(fromIndex).pop(to.peek(j));
		}
		if (!this.getStack(fromIndex).peek().isFaceUp()) {//������һ����Ϊ����
			this.getStack(fromIndex).peek().setFaceUp(true);
		}
	}

	// ���������ȫ�����겢��discardstackҲû����
	public void reset_all() {
		notifyListeners();
	}

	// ���������ȫ������ ����discardstack����
	public void reset() {
		for (int i = 0; i < this.getStack(1).size(); i++) {
			this.getStack(0).init(this.getStack(1).peek(i));//���ƶѵ��ƷŻط��ƶ�
		}
		this.getStack(1).clear();//���ƶ������ͷ�
		notifyListeners();
	}
	
	//������Ҫ�ƶ��Ŀ������ƶѼ�Ĵ��ݣ���ͨ���ڼ��а��д���ַ���ʵ���ƶѼ���Ϣ�Ĵ��ݵģ����ｫ�ַ����б�ʾ�Ŀ���ת������ʵ���ƶѡ�
	public CardStack StringToStack(String pString) {
		if (pString != null && pString.length() > 0) {
			String[] tokens = pString.split(SEPARATOR);
			CardStack aCards = new CardStack();
			for (int i = 0; i < tokens.length; i++) {
				aCards.init(Card.get(tokens[i]));
			}
			for (int i = 0; i < aCards.size(); i++) {
				aCards.peek(i).setFaceUp(true);
			}
			return aCards;
		}
		return null;
	}
	//��ȡ���а����ַ�����ʾ�Ŀ�������˵�һ�ſ���
	public Card getTop(String result) {
		if (result != null && result.length() > 0) {
			String[] tokens = result.split(SEPARATOR);
			Card aCards[];
			aCards = new Card[tokens.length];
			for (int i = 0; i < tokens.length; i++) {
				aCards[i] = Card.get(tokens[i]);
			}
			return aCards[0];
		}
		return null;
	}
	//���ƶ�ת��Ϊ�ַ����ķ���������һ���ƶѣ�ͨ����ȥÿһ���Ƶ�Ψһ���֣���ȡ���Ƶ����ƣ�ƴ�����ַ�����
	public String serialize(Card pCard, int aIndex) {
		CardStack temp_stack = GameModel.instance().getSubStack(pCard, aIndex);
		String result = "";
		Card temp_card;
		for (int i = 0; i < temp_stack.size(); i++) {
			temp_card = temp_stack.peek(i);
			result += temp_card.getIDString() + SEPARATOR;
		}
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}
}
