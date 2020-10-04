package core;

import lombok.Data;

import java.util.List;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-26
 */
@Data
public class Field {

    private String position;
    private String name;
    private String table;
    private String type;
    private String length;
    private String description;
    private TestParams testParams;
}
