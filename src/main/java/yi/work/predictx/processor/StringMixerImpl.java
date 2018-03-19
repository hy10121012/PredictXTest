package yi.work.predictx.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hy10121012 on 2018/3/18.
 */
public class StringMixerImpl implements StringMixer {

    private static final Log log = LogFactory.getLog(StringMixerImpl.class);

    private PrefixGenerator prefixGenerator;

    private StringMixerImpl() {
    }

    StringMixerImpl(PrefixGenerator prefixGenerator) {
        this.prefixGenerator = prefixGenerator;
    }

    @Override
    public String min(String... strings) {
        if (strings == null) {
            return "";
        }
        Long startTime = System.currentTimeMillis();
        List<ChartInfo> l = generateString(strings);
        String s = String.join("/", l);

        Long endTime = System.currentTimeMillis();

        log.debug("total performance: " + (endTime - startTime) + " ms");


        return s;
    }

    @Override
    public String minWithJson(String... strings) {
        if (strings == null) {
            return "";
        }
        Long startTime = System.currentTimeMillis();

        List<ChartInfo> chartInfos = generateString(strings);
        JSONArray json = new JSONArray();
        chartInfos.forEach(chartInfo -> json.put(chartInfo.toString()));
        Long endTime = System.currentTimeMillis();
        log.debug("total performance: " + (endTime - startTime) + " ms");
        return json.toString();
    }


    private List<ChartInfo> generateString(String[] strings) {
        Long startTime = System.currentTimeMillis();

        List<List<ChartInfo>> strInfos = new ArrayList<>(strings.length);
        Integer i = 1;
        for (String curStr : strings) {
            strInfos.add(analyzeSingleStr(i++, curStr));
        }
        Long endTime = System.currentTimeMillis();
        log.debug("analysing performance: " + (endTime - startTime) + " ms");
        List<ChartInfo> consolidate = new ArrayList<>();

        startTime = System.currentTimeMillis();

        strInfos.forEach(strInfo -> {
            strInfo.forEach(cInfo -> {
                if (!consolidate.contains(cInfo)) {
                    consolidate.add(cInfo);
                } else {
                    int k = consolidate.indexOf(cInfo);
                    if (consolidate.get(k).getOccurency().compareTo(cInfo.getOccurency()) < 0) {
                        consolidate.set(k, cInfo);
                    } else if (consolidate.get(k).getOccurency().equals(cInfo.getOccurency())) {
                        consolidate.get(k).setPreFix(prefixGenerator.generateEqualedPrefix(consolidate.get(k).getPrefix(), cInfo.getPrefix()));
                    }
                }

            });
        });
        endTime = System.currentTimeMillis();

        List strToReturn = consolidate.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        String s = String.join("/", strToReturn);
        log.info("generating string: " + s);
        log.debug("concatinating performance: " + (endTime - startTime) + " ms");
        return strToReturn;
    }


    private List<ChartInfo> analyzeSingleStr(Integer prefix, String s) {
        if (prefix == null || Strings.isEmpty(s)) {
            return new ArrayList<>();
        }

        Map<Character, ChartInfo> map = new HashMap<>();

        for (Character x : s.toCharArray()) {
            int chartInt = (int) x;
            if (chartInt >= 97 && chartInt <= 122) {
                map.putIfAbsent(x, new ChartInfo(prefix.toString(), x, 0));
                map.get(x).plusOne();
            }
        }
        List<ChartInfo> stringInfoList = map.values().stream().filter(x -> x.getOccurency() > 1).collect(Collectors.toList());
        return stringInfoList;
    }


    private final class ChartInfo implements Comparable<ChartInfo>, CharSequence {
        private final Character character;
        private Integer occurency;
        private String prefix;

        ChartInfo(String prefix, Character character, Integer occurency) {
            this.character = character;
            this.occurency = occurency;
            this.prefix = prefix;

        }

        Character getCharacter() {
            return character;
        }

        void plusOne() {
            occurency++;
        }

        Integer getOccurency() {
            return occurency;
        }

        String getPrefix() {
            return prefix;
        }

        void setPreFix(String preFix) {
            this.prefix = preFix;
        }

        @Override
        public int compareTo(ChartInfo o) {
            if (o.getOccurency().compareTo(this.getOccurency()) != 0) {
                return this.getOccurency().compareTo(o.getOccurency());
            } else {

                if (o.getPrefix().compareTo(this.getPrefix()) != 0) {
                    if (o.getPrefix().split(",").length > this.getPrefix().split(",").length) {
                        return 1;
                    }
                    if (o.getPrefix().split(",").length < this.getPrefix().split(",").length) {
                        return -1;
                    } else {
                        return o.getPrefix().compareTo(this.getPrefix());
                    }
                } else {
                    return o.getCharacter().compareTo(this.getCharacter());
                }
            }
        }


        @Override
        public int length() {
            return this.toString().length();
        }

        @Override
        public char charAt(int index) {
            return this.toString().charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return this.toString().subSequence(start, end);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(prefix).append(":");
            sb.append(generateChartFromOccurency());
            return sb.toString();
        }

        String generateChartFromOccurency() {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < occurency; k++) {
                sb.append(character);
            }
            return sb.toString();
        }

        public boolean equals(Object o) {
            if(o==null)return false;
            return o instanceof ChartInfo && ((ChartInfo) o).getCharacter().equals(this.getCharacter());


        }

        public int hashCode() {
            return this.getCharacter().hashCode();
        }
    }


}
