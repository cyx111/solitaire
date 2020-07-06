package Card;
import java.util.ArrayList;
//ʮ�����ƶѵĸ��࣬��һ��ArrayList����ά�������е��ơ�
//�������ж��еĹ�ͬ���������ж��ƶ��Ƿ�Ϊ�ա�����ƣ��Ƴ��ƣ���öѶ��Ƶȡ�
public class CardStack implements CardStack_Interface {
	private ArrayList<Card> pokers_card;
    public CardStack()
    {
        pokers_card = new ArrayList<Card>();
    }
    //��ȡlist�Ĵ�С
    public int size() {
        return this.pokers_card.size();
    }
    //��ȡ������Ƭ
    public ArrayList<Card> getPokers_card()
    {
        return this.pokers_card;
    }
    public Card peek() {
    //����ƶ��������ö������ƣ�������һ�ź���A
       if(this.pokers_card.size() >0)
       {
           return this.pokers_card.get(this.pokers_card.size() - 1);
       }
      else
      {
           return new Card(Rank.ACE,Suit.HEARTS);
      }
	}
	//���ƶ����һ�ſ��ƣ���ӵ����
    public void init(Card pCard)
    {
        this.pokers_card.add(pCard);
    }
    //��ȡָ��λ�õĿ�Ƭ
    public Card peek(int index)
    {
        if(index >= 0 && index < size())
        {
            return this.pokers_card.get(index);
        }
        return null;
    }
    //list�Ƿ�Ϊ��
    public boolean isEmpty() {
        return pokers_card.size() == 0;
    }
    //pushһ�鿨��
    public void push(CardStack pStack, int index) {
        for(int i = index; i < pStack.size(); i++) {
            this.pokers_card.add(pStack.peek(i));
        }
    }
    public void push(CardStack pStack) {
        for(int i = 0; i < pStack.size(); i++) {
            this.pokers_card.add(pStack.peek(i));
        }
    }
    //ɾ������
    public void pop(Card pCard) {
        if(!isEmpty())
        {
            for(int i = 0 ; i <this.pokers_card.size() ; i++)
            {
                if(pCard.getSuit() == this.pokers_card.get(i).getSuit() &&pCard.getRank() == this.pokers_card.get(i).getRank() )
                    this.pokers_card.remove(i);
            }
        }
    }
    //ɾ����������
    public void pop() {
        if(!isEmpty())
            this.pokers_card.remove(pokers_card.size()-1);
    }
    public void clear() {
        this.pokers_card.clear();
    }
}
