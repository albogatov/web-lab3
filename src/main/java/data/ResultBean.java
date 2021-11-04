package data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ResultBean {
    private Result newResult = new Result();

    private List<Result> employees = new ArrayList<Result>();
}
