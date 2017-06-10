package upandgo.client.view;


import org.gwtbootstrap3.client.ui.Modal;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerControlsStyle;

public class SchedulerControlsView extends VerticalPanel{

	private SchedualerControlsStyle scStyle = Resources.INSTANCE.schedualerControlsStyle();

	Button buildSchedule = new Button("<i class=\"fa fa-calendar\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
	Button clearSchedule = new Button("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;נקה מערכת");
	Button setConstrains = new Button("<i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הגדר אילוצים");
	Button nextSchedule = new Button("מערכת באה&nbsp;&nbsp;<i class=\"fa fa-arrow-left\" aria-hidden=\"true\"></i>");
	Button prevSchedule = new Button("<i class=\"fa fa-arrow-right\" aria-hidden=\"true\"></i>&nbsp;&nbsp;מערכת קודמת");
	Button saveSchedule = new Button("<i class=\"fa fa-floppy-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;שמור מערכת");
	
	SchedulerConstraintsView schedualerConstraintsView;

	public SchedulerControlsView(){
		this.schedualerConstraintsView = new SchedulerConstraintsView();
    	InitializePanel();
    	scStyle.ensureInjected();
    }
	
	public SchedulerControlsView(SchedulerConstraintsView scv){
		this.schedualerConstraintsView = scv;
    	InitializePanel();
    	scStyle.ensureInjected();
    }


	private void InitializePanel() {
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.setStyleName(scStyle.SchedualerControlsPanel());
		this.add(buildSchedule);
		this.add(clearSchedule);
		this.add(setConstrains);
		this.add(nextSchedule);
		this.add(prevSchedule);
		this.add(saveSchedule);
		//this.add(new SchedualerConstraintsView());
		
		buildSchedule.setStyleName("btn btn-success");
		clearSchedule.setStyleName("btn btn-danger");
		setConstrains.setStyleName("btn btn-warning");
		nextSchedule.setStyleName("btn btn-primary");
		prevSchedule.setStyleName("btn btn-primary");
		saveSchedule.setStyleName("btn btn-info");
		
		nextSchedule.setEnabled(false);
		prevSchedule.setEnabled(false);

		buildSchedule.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				buildSchedule.setHTML("<i class=\"fa fa-spinner fa-spin\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
			}
    		
    	});
		
		setConstrains.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				Modal constraintsBox = new Modal();
				constraintsBox.setFade(true);
				constraintsBox.setTitle("הגדרת אילוצים");
				constraintsBox.add(schedualerConstraintsView);
				constraintsBox.show();
				
			}
		});

	}
	
	public void setPrevEnable(boolean enable){
		prevSchedule.setEnabled(enable);;
	}
	
	public void setNextEnable(boolean enable){
		nextSchedule.setEnabled(enable);
	}
	
	public void scheduleBuilt(){
		buildSchedule.setHTML("<i class=\"fa fa-calendar-check-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
	}
}
