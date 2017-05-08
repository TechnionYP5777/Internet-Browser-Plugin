package upandgo.client;
import java.util.ArrayList;
import java.util.List;

import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipPlacement;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontStyle;

/**
 * 
 * @author danabra
 * @since 7-04-17
 * 
 * GUI class for list of courses and course selection 
 * 
 */
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipContentProvider;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipPlacement;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipPlacementProvider;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipTrigger;
import static com.arcbees.gquery.tooltip.client.Tooltip.Tooltip;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasCellPreviewHandlers;

import upandgo.client.presenter.CourseListPresenter;
import upandgo.shared.entities.course.CourseId;

public class CourseSelectionGUI extends LayoutPanel implements CourseListPresenter.Display {
    private CellTable<CourseId> ccl = new CellTable<>(); //chosen courses
    private Label cc = new Label("קורסים שנבחרו:");
    private CellTable<CourseId> scl = new CellTable<>(); //all courses list
    private Label sc = new Label("בחר קורסים:");
    private ListBox faculties = new ListBox(); //faculties
    private TextBox searchCourse = new TextBox();
    private List<CourseId> courses; //all the courses that user didn't choose yet
    private ScrollPanel cclp = new ScrollPanel();
    private ScrollPanel sclp = new ScrollPanel();
    public CourseSelectionGUI(){
    	courses = new ArrayList<>();
    	courses.add(new CourseId("1234", "חישביות"));
    	courses.add(new CourseId("1234", "הסתברות"));
    	courses.add(new CourseId("1234", "סיבוכיות"));
    	courses.add(new CourseId("1234", "פרויקט שנתי חלק א"));
    	courses.add(new CourseId("1234", "פרויקט שנתי חלק ב"));
    	courses.add(new CourseId("1234", "אוטומטים ושפות פורמליות"));
    	courses.add(new CourseId("1234", "לוגיקה"));
    	courses.add(new CourseId("1234", "קומבינטוריקה למדעי המחשב"));
    	courses.add(new CourseId("1234", "שפות תכנות"));
    	courses.add(new CourseId("1234", "מבני נתונים"));
    	courses.add(new CourseId("1234", "מבנה מחשבים ספרתיים"));
    	courses.add(new CourseId("1234", "תכנות מקביליו מבוזר"));
    	courses.add(new CourseId("1234", "מערכות מסדי נתונים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));
    	courses.add(new CourseId("1234", "אלגוריתמים"));

    	InitializePanel();
    	Resources.INSTANCE.courseListStyle().ensureInjected();

    }
    private void InitializePanel(){
    	// chosen course list initialization
    	ccl.addStyleName(Resources.INSTANCE.courseListStyle().ChosenCourses());
    	ccl.setWidth("100%");
    	TextColumn<CourseId> course = new TextColumn<CourseId>(){
    	      @Override
    	      public String getValue(CourseId object) {
    	        return object.getTitle();
    	      }
		};
		TextColumn<CourseId> course1 = new TextColumn<CourseId>(){
  	      @Override
  	      public String getValue(CourseId object) {
  	        return object.getTitle();
  	      }
		};
	    ccl.addColumn(course);
        ccl.setRowCount(0, true);
        ccl.setVisibleRange(0, courses.size());
	    ccl.setRowData(0,courses);
	    cclp.add(ccl);
	    cclp.setHeight("25em");
	    cclp.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
	    cclp.getElement().getStyle().setBorderWidth(1, Unit.PX);
	    cclp.getElement().getStyle().setBorderColor("LightGray");

    	//all courses list initialization
    	scl.setWidth("100%");
    	scl.addColumn(course1);
        scl.setRowCount(courses.size(), true);
        scl.setVisibleRange(0, courses.size());
	    scl.setRowData(0,courses);
	    sclp.add(scl);
	    sclp.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
	    sclp.getElement().getStyle().setBorderWidth(1, Unit.PX);
	    sclp.getElement().getStyle().setBorderColor("LightGray");
    	
    	//initializing faculty selection
    	faculties.setWidth("100%");
    	faculties.setHeight("2em");
    	faculties.addItem("פקולטה");
    	faculties.addItem("מדעי המחשב");
    	faculties.addItem("חשמל");
    	faculties.addItem("פיזיקה");
    	faculties.addItem("מתמטיקה");
    	
    	//search course text field initialization
    	searchCourse.setHeight("2em");
    	searchCourse.setWidth("100%");
    	searchCourse.setTitle("חפש קורסים");
    	searchCourse.getElement().setPropertyString("placeholder", "חפש קורסים...");

    	cc.getElement().getStyle().setFontStyle(FontStyle.OBLIQUE);
    	cc.getElement().getStyle().setFontSize(1.2, Unit.EM);
    	cc.getElement().getStyle().setColor("Red");
    	sc.getElement().getStyle().setFontStyle(FontStyle.OBLIQUE);
    	sc.getElement().getStyle().setFontSize(1.2, Unit.EM);
    	sc.getElement().getStyle().setColor("Red");
    	
    	
    	//adding widgets to panel
    	this.getElement().getStyle().setMargin(10, Unit.PX);
    	this.add(cc);
	    this.add(cclp);
	    this.add(sc);
	    this.add(faculties);
	    this.add(searchCourse);
	    this.add(sclp);
	    this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(cclp, 2,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 29.3,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(faculties, 31.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(searchCourse, 34.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sclp, 37.5,  Unit.EM, 0, Unit.EM);
    }
    
    // Implementation of Display
	@Override
	public HasCellPreviewHandlers<CourseId> getSelectedCoursesList() {
		return ccl;
	}
	@Override
	public HasCellPreviewHandlers<CourseId> getNotSelectedCoursesList() {
		return scl;
	}
	@Override
	public HasChangeHandlers getFacultyDropList() {
		return faculties;
	}
	@Override
	public HasKeyUpHandlers getCourseSearch() {
		return searchCourse;
	}
	@Override
	public void setSelectedCourses(List<CourseId> is) {
		
        ccl.setRowCount(is.size(), true);
        ccl.setVisibleRange(0, is.size());
	    ccl.setRowData(0,is);
		
	}
	@Override
	public void setNotSelectedCourses(List<CourseId> is) {

		Window.alert(is.get(0).getTitle());
		scl.setRowCount(is.size(), true);
        scl.setVisibleRange(0, is.size());
	    scl.setRowData(0,is);
		
	}
	@Override
	public void setFaculties(List<String> faculties) {
		for(String s : faculties)
			this.faculties.addItem(s);
		
	}
	@Override
	public int getSelectedCourseRow(CellPreviewEvent<CourseId> i) {
		return i.getIndex();
	}
	@Override
	public int getUnselectedCourseRow(CellPreviewEvent<CourseId> i) {
		return i.getIndex();
	}
	@Override
	public int getHoveredSelectedCourseRow(CellPreviewEvent<CourseId> i) {
		return i.getIndex();
	}
	@Override
	public int getHoveredNotSelectedCourseRow(CellPreviewEvent<CourseId> i) {
		return i.getIndex();
	}
	@Override
	public int getSelectedFacultyRow(@SuppressWarnings("unused") ChangeEvent e) {
		return this.faculties.getSelectedIndex();
	}
	@Override
	public String getCourseQuery(@SuppressWarnings("unused") KeyUpEvent e) {
		return this.searchCourse.getValue();
	} 
    
}
