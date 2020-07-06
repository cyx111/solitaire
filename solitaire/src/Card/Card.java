package Card;
//�����Ƶ�һЩ���ԣ�������ɫ����ֵ���Ƿ����泯�ϵȡ�
//���峣����ʾ��ɫ�ĽǱ꣬������������ֱ�ά���ƵĻ�ɫ����ֵ��
public class Card {
	//��������Ϊ˽�У�ͨ�����еķ����ı�
    private  final Rank aRank;//��С
    private  final Suit aSuit;//��ɫ
    private boolean faceUp = false;//�˿����Ƿ����泯��
    private boolean isRed ;
    //����Ϊ���е�
    //52���Ƶĳ�ʼ��
	private static final Card[][] CARDS = new Card[Suit.values().length][];
	// ��ʼ��ÿһ���ƵĻ�ɫ�ʹ�С������һ����ţ��Ա����ͨ����Ż�ȡ���Ӧ�Ŀ���
    static
    {
        for( Suit suit : Suit.values() )//����
        {
            CARDS[suit.ordinal()] = new Card[Rank.values().length];
            for( Rank rank : Rank.values() )
            {
                CARDS[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
            }
        }
    }
    //���췽��
    public Card(Rank pRank, Suit pSuit)
    {
        aRank = pRank;
        aSuit = pSuit;
        if(pSuit == Suit.HEARTS || pSuit == Suit.DIAMONDS)
        {
            isRed = true;
        }
        else
        {
            isRed = false;
        }
    }
    //��ȡ�����Ƶı��
    public String getIDString()
    {
        return Integer.toString(getSuit().ordinal() * Rank.values().length + getRank().ordinal());
    }
    //�����������Ƿ���
    public boolean isFaceUp() {
        return faceUp;
    }
    //���������Ƶĳ���
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
    //�����Ƿ�Ϊ��ɫ
    public boolean isRed() {
        return isRed;
    }
    //��û�ɫ
    public Suit getSuit() {
        return this.aSuit;
    }
    //���ݱ�Ż�ȡ��Ӧ����
    public static Card get(String pId)
    { 
    	if(pId != null)
        {
            int id = Integer.parseInt(pId);
            return get(Rank.values()[id % Rank.values().length], Suit.values()[id / Rank.values().length]);
        }
        return null;
    }
    //���ݻ�ɫ�ʹ�С��ȡ��Ӧ����
    public static Card get(Rank pRank, Suit pSuit)
    {
        if( pRank != null && pSuit != null)
        {
            return CARDS[pSuit.ordinal()][pRank.ordinal()];
        }
        return null;
    }
    //��ȡ�ƵĴ�С
    public Rank getRank() {
        return this.aRank;
    }
}
