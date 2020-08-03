package utils;

import com.alibaba.fastjson.JSON;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-07-27
 */
public class XMLParse {

    public static Map<String, String> parseXml(String fileName, String ids) {
        HashMap<String, String> xmlContents = new HashMap<>();
        try {
            FileInputStream xmlFile = new FileInputStream(new File(fileName));

            Document doc = new SAXReader().read(xmlFile);
            List<Element> elements = doc.getRootElement().elements();

            for (Element ele : elements) {
                List<Element> eleContents = ele.elements();
                HashMap<String, String> xmlContentMap = new HashMap<>();
                String idsValue = "";
                for (Element content : eleContents) {
                    if (content.getName().equals(ids)) {
                        idsValue = content.getTextTrim();
                    }
                    xmlContentMap.put(content.getName(), content.getTextTrim());
                }
                String xmlContent = JSON.toJSONString(xmlContentMap);
                xmlContents.put(idsValue, xmlContent);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return xmlContents;
    }
}
