package yi.work.predictx.webapi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import yi.work.predictx.processor.StringMixer;


/**
 * Created by hy10121012 on 2018/3/18.
 */

@RestController
public class StringMixerResources {

    @Autowired()
    @Qualifier(value = "basicMixer")
    private StringMixer basicStringMixer;

    @Autowired()
    @Qualifier(value = "advancedMixer")
    private StringMixer advancedStringMixer;
    private static final Log log = LogFactory.getLog(StringMixerResources.class);


    @RequestMapping(value = "mixString",method = RequestMethod.POST)
    @ResponseBody
    public String generateMixString(@RequestParam String[] strings,String type){

        String returnStr;
        if("json".equals(type)){
            returnStr = basicStringMixer.minWithJson(strings);
        }else{
            returnStr = basicStringMixer.min(strings);
        }
        log.info("returning: "+strings);
        return returnStr;
    }

    @RequestMapping(value = "advancedMixString",method = RequestMethod.POST)
    @ResponseBody
    public String generateAdvancedMixString(@RequestParam String[] strings,String type){

        String returnStr;
        if("json".equals(type)){
            returnStr = advancedStringMixer.minWithJson(strings);
        }else{
            returnStr = advancedStringMixer.min(strings);
        }
        log.info("returning: "+strings);
        return returnStr;
    }

}
