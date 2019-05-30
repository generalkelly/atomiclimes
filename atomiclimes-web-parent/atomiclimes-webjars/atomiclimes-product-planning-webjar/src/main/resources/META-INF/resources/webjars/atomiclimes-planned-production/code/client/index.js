import $ from 'jquery';
import 'popper.js'
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css'
import './main.css'
import limesLogo from './atomiclimes.svg';

import AtomicLimesPlannedProduction from './atomiclimes-planned-production';

var plannedProduction = new AtomicLimesPlannedProduction();
$('.limesLogo').attr('src', limesLogo);


 plannedProduction.atomicLimesPlannedProduction('.planned-production');
 