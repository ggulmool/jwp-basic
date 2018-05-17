package core.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private View view;
    private Map<String, Object> model;

    public ModelAndView(View view) {
        this.view = view;
        model = new HashMap<>();
    }

    public View getView() {
        return view;
    }

    public ModelAndView addObject(String name, Object obj) {
        model.put(name, obj);
        return this;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }
}
