package upandgo.client.view;

import upandgo.shared.entities.LocalTime;
import java.util.List;

import org.gwtbootstrap3.client.ui.InlineCheckBox;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources;
import upandgo.client.Resources.MainStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.LessonGroup;

public class SchedulerView extends LayoutPanel implements SchedulerPresenter.Display{

	private MainStyle style = Resources.INSTANCE.mainStyle();
	TimeTableView timeTableView = new TimeTableView();
	ScrollPanel scrollableTimeTable = new ScrollPanel(timeTableView);
	SchedulerConstraintsView schedualerConstraintsView = new SchedulerConstraintsView();
	SchedulerControlsView schedualerControlsView = new SchedulerControlsView(schedualerConstraintsView);

	public SchedulerView(){
		InitializePanel();
		style.ensureInjected();
	}
	
	private void InitializePanel(){
		// needs to be injected
		
		scrollableTimeTable.addStyleName(style.scrollableTimeTable());
		

		this.setHeight("100%");
		this.add(scrollableTimeTable);
		this.setWidgetLeftRight(scrollableTimeTable, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetTopBottom(scrollableTimeTable, 1, Unit.EM, 8, Unit.EM);
		this.add(schedualerControlsView);
		this.setWidgetLeftRight(schedualerControlsView, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(schedualerControlsView, 1, Unit.EM, 3, Unit.EM);
/*		this.add(constraintsView);
		this.setWidgetLeftRight(constraintsView, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(constraintsView, 0, Unit.EM, 3, Unit.EM);
		this.setWidgetHorizontalPosition(constraintsView, Layout.Alignment.END);
*/		
		}
	
	
	@Override
	public HasClickHandlers clearSchedule() {
		return schedualerControlsView.clearSchedule;
	}

	@Override
	public HasClickHandlers buildSchedule() {
		return schedualerControlsView.buildSchedule;
	}

	@Override
	public HasClickHandlers nextSchedule() {
		return schedualerControlsView.nextSchedule;
	}

	@Override
	public HasClickHandlers prevSchedule() {
		return schedualerControlsView.prevSchedule;
	}

	@Override
	public HasClickHandlers saveSchedule() {
		return schedualerControlsView.saveSchedule;
	}
 	
	@Override
	public Widget getAsWidget() {
		return this.asWidget();
	}

	@Override
	public void setSchedule(List<LessonGroup> schedule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HasClickHandlers getDaysOffValue() {
		return schedualerConstraintsView.daysOffCB;
	}

	@Override
	public boolean isDayOffChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public HasClickHandlers getMinWindowsValue() {
		return schedualerConstraintsView.minWindowsCB;
	}

	@Override
	public boolean isMinWindowsChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public HasClickHandlers getStartTimeValue() {
		return schedualerConstraintsView.startTimeCB;
	}

	@Override
	public boolean isStartTimeChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public LocalTime getReqStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandlers getFinishTimeValue() {
		return schedualerConstraintsView.finishTimeLB;
	}

	@Override
	public boolean isFinishTimeChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public LocalTime getReqFinishTime() {
		// TODO Auto-generated method stub
		return null;
	}
}