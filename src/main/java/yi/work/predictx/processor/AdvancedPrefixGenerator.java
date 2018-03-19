package yi.work.predictx.processor;

/**
 * Created by hy10121012 on 2018/3/18.
 */
public class AdvancedPrefixGenerator implements PrefixGenerator{

    public String generateEqualedPrefix(String oriPreifx,String newChartInfo){
        return oriPreifx.concat(","+newChartInfo);
    }



}
