import $ from 'jquery';
import './production-item-chooser.css';
import 'jquery.nicescroll';
import 'bootstrap'

export default class AtomicLimesPlannedProduction{

    constructor(){

    }
    
    submit(){
	if(window.getProductionPlanningByDate){
	    getProductionPlanningByDate(JSON.stringify(this.plannedProduction));
	}else{
	    console.log("wicket function submitPlannedProduction was not defined")
	}
    }
    
    atomicLimesPlannedProduction(node){
	
    }
    
}
