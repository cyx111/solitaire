package Card;
//���ƶ�
public class DiscardStack extends CardStack {
	public void push(CardStack pStack, int index) {
		 super.push(pStack, index);
		 //���һ�鿨�ƺ�   ���ƵĶ���Ӧ���ǳ��ϵ�
		 if(!this.isEmpty())
			 this.peek().setFaceUp(true);
	}
}
