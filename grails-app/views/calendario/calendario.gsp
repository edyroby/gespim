<html>
<head>
<style>
		body { font-size: 100%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
<link rel='stylesheet' type='text/css' href='${resource(dir:'css', file:'fullcalendar.css')}' />
<link rel='stylesheet' type='text/css' href='${resource(dir:'css/redmond', file:'jquery-ui-1.8.13.custom.css')}' />
<g:javascript src='jQuery/jquery-1.5.1.min.js'/>
<g:javascript src='jQuery/jquery-ui-1.8.12.custom.min.js'/>
<g:javascript src='fullcalendar.min.js'/>
<script type="text/javascript">

$(document).ready(function() {

    // page is now ready, initialize the calendar...

    var startDate = null

    $('#calendar').fullCalendar({
        // put your options and callbacks here
        monthNames:["Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre",
                    "Ottobre","Novembre","Dicembre"],
        monthNamesShort:["Gen","Feb","Mar","Apr","Mag","Giu","Lug","Ago","Set",
                    "Ott","Nov","Dic"],
        dayNames:["Domenica","Luned&igrave;","Marted&igrave;","Mercoled&igrave;","Gioved&igrave;","Venerd&igrave;","Sabato"],
        dayNamesShort:["Dom","Lun","Mar","Mer","Gio","Ven","Sab"],
        firstDay:1,
        minTime: 6,
        maxTime: 19,
        columnFormat:{
				month:'ddd',
				week:'ddd d/M',
				day: 'dddd d/M'
            },
        buttonText:{
			today:'oggi',
			month:'mese',
			week:'settimana',
			day:'giorno'
            },
        ignoreTimezone: false,
        dayClick: function(date, allDay, jsEvent, view) {
            if (allDay) {
                $('#calendar').fullCalendar('gotoDate', date)
                $('#calendar').fullCalendar('changeView', 'agendaDay')
            }else{
                startDate = date
                $( "#dialog-form" ).dialog( "open" );
            }
        },

        header: {
            left: 'title',
            center: '',
            right: 'today agendaDay,agendaWeek,month prev,next'
        },

        events:'${createLink(controller:'appuntamento', action:'appuntamentiFeed')}',

        slotMinutes: 10,

        eventDrop: function(event, dayDelta, minuteDelta, allDay, revertFunc) {

            $.ajax({
                url:'${createLink(controller:'appuntamento', action:'moveAppuntamento')}',
                data: {eventId: event.id, dayDelta: dayDelta, minuteDelta: minuteDelta}
                })
        },

        eventResize: function(event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view) {
            $.ajax({
                url:'${createLink(controller:'appuntamento', action:'resizeAppuntamento')}',
                data: {eventId: event.id, dayDelta: dayDelta, minuteDelta: minuteDelta},
                contentType: "application/json"
                })

        }


    });

    var title = $('#title')
    var description = $('#description')
    var start = $('#start')
    $('#dialog-form').dialog({
                autoOpen: false,
                height: 400,
                width: 500,
                modal: true,
                buttons: {
                            "Create event": function() {
                                                $.ajax({
                                                    url:'${createLink(controller:'appuntamento', action:'saveAppuntamento')}',
                                                    data: {oggetto: title.val(),
                                                           inizio: startDate.getTime(),
                                                           note: description.val()},
                                                    contentType: 'application/json',
                                                    success: function(data, textStatus, jqXHR) {
                                                        $('#dialog-form').dialog('close')
                                                        $('#calendar').fullCalendar('refetchEvents')
                                                    }

                                                })


                                            }
                         }            })

});

</script>
</head>
<body>
    <div id="calendar"></div>
    
    <div id="dialog-form" title="Create new event">
        <p class="validateTips">All form fields are required.</p>

        <form>
        <fieldset>
            <label for="title">Title</label>
            <input type="text" name="title" id="title" class="text ui-widget-content ui-corner-all" />
            <label for="title">Description</label>
            <textarea name="description" id="description" cols="40" rows="5" class="ui-widget-content ui-corner-all"></textarea>
            </fieldset>
        </form>
    </div>
</body>
</html>
