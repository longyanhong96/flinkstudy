import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import core.Field;
import core.Fields;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.json.XML;
import utils.XMLParse;

import java.io.*;
import java.util.List;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-26
 */
public class XMLParseTest {
    public static void main(String[] args) throws Exception {
//        String fileName = "H:\\myworkspace\\flinkstudy\\src\\main\\resources\\realtime.Fields";
//        FileInputStream xmlFile = new FileInputStream(new File(fileName));
//
//        Document doc = new SAXReader().read(xmlFile);
//        List<Element> elements = doc.getRootElement().elements();
//
//        for (Element element : elements) {
//            System.out.println(element.getName());
//        }

//        System.out.println(xml2jsonString());

        System.out.println(parseXml());

//        String xmlJson = XMLParse.xml2json("H:\\myworkspace\\flinkstudy\\src\\main\\resources\\realtime.xml");
//        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(xmlJson);
//        System.out.println(jsonObject.toJSONString());
//        System.out.println(jsonObject.getString("fields"));
//
//        String fields = "[{\"name\":\"transInfo\",\"length\":16,\"description\":\"信审号\",\"position\":0,\"type\":1,\"table\":\"GDB_BASE_BANK\"},{\"name\":\"id_nbr\",\"length\":16,\"description\":\"数据批号\",\"position\":1,\"type\":1,\"table\":\"GDB_BASE_BANK\"},{\"name\":\"name\",\"length\":20,\"description\":\"申请代号\",\"position\":2,\"type\":1,\"table\":\"GDB_BASE_BANK\"}]";
//        System.out.println(JSON.parseObject(fields, Field.class));

//        List<Field> list = JSON.parseArray(fields, Field.class);
//        for (Field field : list) {
//            System.out.println(field);
//        }

//        String fieldsString = "{\"field\":[{\"name\":\"transInfo\",\"length\":16,\"testParams\":{\"a\":1,\"b\":2},\"description\":\"信审号\",\"position\":0,\"type\":1,\"table\":\"GDB_BASE_BANK\"},{\"name\":\"id_nbr\",\"length\":16,\"description\":\"数据批号\",\"position\":1,\"type\":1,\"table\":\"GDB_BASE_BANK\"},{\"name\":\"name\",\"length\":20,\"description\":\"申请代号\",\"position\":2,\"type\":1,\"table\":\"GDB_BASE_BANK\"}]}";
//        System.out.println(JSON.parseObject(fieldsString, Fields.class));
//        System.out.println(XMLParse.xml2Object("H:\\myworkspace\\flinkstudy\\src\\main\\resources\\realtime.xml", Fields.class));
    }

    public static String xml2jsonString() throws Exception {
        String fileName = "H:\\myworkspace\\flinkstudy\\src\\main\\resources\\realtime.xml";
        InputStream inputStream = new FileInputStream(new File(fileName));
        String xml = IOUtils.toString(inputStream);
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj.toString();
    }

    public static String parseXml() {
        FileInputStream in = null;
//        String inputXML = null;
//        try {
//            String fileName = "H:\\myworkspace\\flinkstudy\\src\\main\\resources\\realtime.xml";
//            in = new FileInputStream(new File(fileName));
//            inputXML = IOUtils.toString(in);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return inputXML;


        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<Datas>\n" +
                "\t<segment>\n" +
                "\t\t<column>xxx</column>\n" +
                "\t\t<type>\n" +
                "\t\t\t<rule>\n" +
                "\t\t\t\t<name>xxx</name>\n" +
                "\t\t\t\t<parm>xxx</parm>\n" +
                "\t\t\t</rule>\n" +
                "\t\t</type>\n" +
                "\t</segment>\n" +
                "\t<segment>\n" +
                "\t\t<column>xxx</column>\n" +
                "\t\t<type>\n" +
                "\t\t\t<typecolumn>xxx</typecolumn>\n" +
                "\t\t\t<typeoperation>xxx</typeoperation>\n" +
                "\t\t\t<typevalue>xxx</typevalue>\n" +
                "\t\t\t<rule>\n" +
                "\t\t\t\t<name>xxx</name>\n" +
                "\t\t\t\t<parm>xxx</parm>\n" +
                "\t\t\t</rule>\n" +
                "\t\t</type>\n" +
                "\t</segment>\n" +
                "</Datas>";
        String xmlJson = XML.toJSONObject(inputXml).toString();
        return xmlJson;
    }
}
