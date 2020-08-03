import utils.XMLParse;
import java.util.Map;

/**
 * Description : 解析xml
 *
 * @author lyh
 * @date 2020-07-22
 */
public class XMLTest {

    public static void main(String[] args) {
//        Map<String, String> map=new HashMap<String,String>();
//        try {
//            InputStream is = new FileInputStream(new File("H:\\myworkspace\\flinkstudy\\src\\main\\resources\\realtime.xml"));
//
//            SAXReader sax = new SAXReader(); //创建解析器
//
//            Document doc = sax.read(is); //创建对应的Document对象
//
//            Element root = doc.getRootElement(); //获取XML文档的根节点对象
//
//            List<Element> list = root.elements();
//
//            for (Element ele : list) {
//                System.out.println(ele.getName());
//                for (Element element : ele.elements()) {
//                    System.out.print(element.getName() + ":");
//                    System.out.print(element.getText() + " ");
//                }
//                System.out.println();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Map<String, String> contents = XMLParse.parseXml("H:\\myworkspace\\flinkstudy\\src\\main\\resources\\realtime.xml", "position");

        for (Map.Entry<String, String> entry : contents.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
