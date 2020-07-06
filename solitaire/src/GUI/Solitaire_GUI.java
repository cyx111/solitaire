package GUI;

import Card.GameModel;
import Card.Suit;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Solitaire_GUI extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 900;
    private static final int MARGIN_OUTER = 15;
    private static final String TITLE = "Solitaire";
    private DeckPileView aDeckPileView = new DeckPileView();//发牌堆
    private DiscardPileView aDiscardPileView = new DiscardPileView();//初始化弃牌堆数组
    private SuitPileView[] aSuitStacks = new SuitPileView[Suit.values().length];//花色堆
    private TablePileView[] aStacks = new TablePileView[TablePile.values().length];
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage pPrimaryStage)//启动函数
    {
        pPrimaryStage.setTitle(TITLE);//设定标题
        GridPane grid = new GridPane();//布局方式
        grid.setHgap(MARGIN_OUTER);//垂直间距
        grid.setVgap(MARGIN_OUTER);//水平间距
        grid.setPadding(new Insets(MARGIN_OUTER));//设置边界为10
        grid.setStyle("-fx-background-image: url('/resources/b.jpg')");//背景图
        grid.add(aDeckPileView, 7, 0);//将节点水平放置
        grid.add(aDiscardPileView, 6, 0);//将节点竖直放置
        for( SuitPile index : SuitPile.values() )
        {//花色堆
            aSuitStacks[index.ordinal()] = new SuitPileView(index);
            grid.add(aSuitStacks[index.ordinal()], 1+index.ordinal(), 0);
        }
        for( TablePile index : TablePile.values() )
        {//桌面堆
            aStacks[index.ordinal()] = new TablePileView(index);
            grid.add(aStacks[index.ordinal()], index.ordinal()+1, 1);

        }
        
        if(GameModel.instance().getStack(9).size() == 13 &&GameModel.instance().getStack(10).size() == 13&&GameModel.instance().getStack(11).size() == 13&&GameModel.instance().getStack(12).size() == 13)
        {
            Text t = new Text();
            t.setText("游戏通关");
            t.setFont(Font.font ("Verdana"));
            t.setFill(Color.BLACK);
            grid.add(t,4,9);
        }
        pPrimaryStage.setResizable(false);
        Scene scene = new Scene(grid, WIDTH, HEIGHT);
        pPrimaryStage.setScene( scene);
        pPrimaryStage.show();
    }
}
