package Card;
//�ȴ�ȡ�õ�ֽ��
public class DeckStack extends CardStack {
	public void push(CardStack pCStack, int index) {
	if(pCStack != null)
	{
	    for(int i = index; i >= 0; --i) {
	    	this.getPokers_card().add(pCStack.peek(i));
	    }
    }
    pCStack.peek().setFaceUp(false);
    //�����ʱ�����ñ��泯��
    }
}
