package Card;
//�����
public class TableStack extends CardStack {
	
	public int getCardIndex(Rank pRank, Suit pSuit) {
        if(!this.isEmpty())
        {//�Ӷ�����ʼ���¿� ����Ŀ��ƣ������������������ ������������Ҫ�ҵ����ţ��Ϳ���ѡ����
            for(int i = this.size() -1 ; i >= 0 ; i--)
            {
                if(this.peek(i).getRank() == pRank && this.peek(i).getSuit() == pSuit)
                        return i;
            }
        }
        return -1;
    }
    public TableStack getSubStack(Card pCard, TableStack stack)
    {
        if(pCard != null)
        {
            int index = stack.getCardIndex(pCard.getRank(),pCard.getSuit());
            TableStack temp_stack = new TableStack();//���Ӵ�����stack����
            for(int i = index ; i< stack.size() ; i++)
            {
                temp_stack.init(stack.peek(i));
            }
            return temp_stack;
        }
        return null;
    }
}
