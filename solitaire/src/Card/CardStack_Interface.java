package Card;
import java.util.ArrayList;

public interface CardStack_Interface {
	//��ſ���
    ArrayList<Card> pokers_card = null;
    //��ȡlist�Ĵ�С
    int size();
    ArrayList<Card> getPokers_card();
    void init(Card pCard);
    //��ȡ������Ƭ
    Card peek();
    //��ȡָ��λ�õĿ�Ƭ
    Card peek(int index);
    //list�Ƿ�Ϊ��
    boolean isEmpty();
    //pushһ�鿨��
    void push(CardStack pStack, int index);
    //ɾ��һ�鿨��
    void pop(Card pCard);
    //ɾ��������Ƭ
    void pop();
    void clear();
}
