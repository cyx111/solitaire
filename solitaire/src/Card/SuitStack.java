package Card;
//四个花色堆
public class SuitStack extends CardStack {
	public void push(CardStack pStack, int index) {
        super.push(pStack, index);
        if (!this.isEmpty()) {
            this.peek().setFaceUp(true);
        }
    }
}
