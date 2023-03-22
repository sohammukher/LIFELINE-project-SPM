document.addEventListener("DOMContentLoaded", function(event){
    var patList = document.querySelector('.view-patient');
    var appointmentList = document.querySelector(".appointment-list-table");
    var doctorsList = document.querySelector(".assign-doctors");
    var allTables = document.querySelectorAll(".list-table");
    var loaderContainer = document.querySelector(".loader-container")
    var patientTable =  document.querySelector('.patient-list-table');
    var counsellorTable =  document.querySelector('.doctor-list-table');
    var appointmentsTable =  document.querySelector('.appointment-list-table');

    var questions = [
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Little interest or pleasure in doing things?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling down, depressed or hopless?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Trouble falling asleep, staying asleep, or sleeping too much?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling tired or having little energy?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Poor appetite or overeating?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling bad about yourself - or that you\'re a failure or have let yourself or your family down?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Trouble concentrating on things, such as reading the newspaper or watching television?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Moving or speaking so slowly that other people could have noticed. Or, the opposite - being so fidgety or restless that you have been moving around a lot more than usual?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Thoughts that you would be better off dead or of hurting yourself in some way?"
    ]

    var responses = {
        "A":"Not At All",
        "B":"Several Days",
        "C":"More Than Half the Days",
        "D":"Nearly Every Day"
    }

    $(document).ready(function(){
        $.ajax({
            url:"http://localhost:9999/getSelfAssessmentScores"
        }).then(function(results){
            var count=1;
            var table_body="";
            results.map((patient)=>{
                var scoreString = patient.question1+patient.question2+patient.question3+patient.question4+
                            patient.question5+patient.question6+patient.question7+patient.question8+patient.question9;
                var scheduleString = patient.first_name+"-"+patient.last_name+"-"+patient.email;
                var refuseString = patient.first_name+"_"+patient.last_name+"_"+patient.email;
                var assignString = patient.first_name+"__"+patient.last_name+"__"+patient.email;
                table_body+=
                    `<tr>
                    <th scope="row">${count}</th>
                    <td class="pat-name">${patient.first_name} ${patient.last_name}</td>
                    <td class="pat-email">${patient.email}</td>
                    <td class="pat-assessment">${patient.self_assessment_score}</td>
                    <td class="pat-accept-reject">
                        <button type="button" class="btn btn-success" id="${scheduleString}" data-toggle="modal" data-target="#myModal">Schedule</button>
                        <button type="button" class="btn btn-danger" id="${refuseString}" data-toggle="modal" data-target="#myModal">Refuse</button>
                        <button type="button" class="btn btn-info" id="${scoreString}" data-toggle="modal" data-target="#myModal">Detailed Score</button>
                        <button type="button" class="btn btn-warning" id="${assignString}" data-toggle="modal" data-target="#myModal">Assign</button>
                    </td>
                  </tr>`;
                count++;
            })
            $("#patient-self-assessment-results").html(table_body);
        })
    })

    $(document).on('click','.assign-doctors', function(event){
        var counselor_email = window.location.href.split("/")[4];
        $.ajax({
            url:"http://localhost:9999/getCounselorAppointments1/"+counselor_email
        }).then(function(results){
            var count=1;
            var table_body="";
            results.map((patient)=>{
                var appointmentLiterals = patient.appointment_time.split(":");
                var appointmentDate = appointmentLiterals[0].substring(0,appointmentLiterals[0].length-3);
                var appointmentTime = appointmentLiterals[0].substring(appointmentLiterals[0].length-2)+":"+appointmentLiterals[1];
                table_body+=
                    `<tr>
                    <th scope="row">${count}</th>
                    <td class="pat-name">${patient.first_name} ${patient.last_name}</td>
                    <td class="pat-email">${patient.email}</td>
                    <td class="pat-schedule">${appointmentDate} ${appointmentTime}</td>
                    <td class="pat-status">${patient.status}</td>
                  </tr>`;
                count++;
            })
            $("#counsellor_appointment_list").html(table_body);
        })
    })

    function scheduleAppointment(event){
            var details = this.id.split("-");
            document.getElementById('myModal').innerHTML = 
            `<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Patient: ${details[0]} ${details[1]}</h4>
            </div>
            
            <div class="modal-body">
            <p>Patient name: </p>
            <input id="name" type="text" value="${details[0]} ${details[1]}"><br>
            <p>Patient email address: </p>
              <input type="text" value="${details[2]}"><br>
              <p>Select a date for the appointment: </p>
              <input id="appointment_date" type="date"><br>
              <input id="appointment_time" type="time" step=2><br>
              <button class="send-appointment-email main-btn" id="${details[2]}">Send Email</button>
              </div>
            `
        }

    $(document).on('click','.btn-success',scheduleAppointment);

    function refusePatient(event){
            var details = this.id.split("_");
        document.getElementById('myModal').innerHTML =
            `<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Patient: ${details[0]} ${details[1]}</h4>
            </div>
            
            <div class="modal-body">
            <p>Patient email address: </p>
              <input type="text" value="${details[2]}"><br>
              <p>Type your message below: </p>
              <textarea id="email_body">Hello ${details[0]} ${details[1]}! 
                After detailed analysis of your self assessment test results, we have decided that you will not be requiring immediate medical attention. Please feel free to revisit our self assessment test in future when you need in help.
                
                Thanks for reaching out to us! We hope you stay healthy!
              </textarea><br>
              <button class="send-email main-btn" id="${details[2]}">Send Email</button>
              </div>`
        }

    $(document).on('click','.btn-danger',refusePatient);

    function openDetailedInfo(event){
            var scores = this.id.split("");
            var html = 
            `<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Self Assessment Details</h4>
            </div>
            <div class="modal-body">
                <table class="table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Question Index</th>
                            <th scope="col">Question</th>
                            <th scope="col">Response</th>
                        </tr>
                    </thead>
                    <tbody>`;
            var count = 1;
            scores.map((score)=>{
                html+=
                `<tr>
                    <td>${count}</td>
                    <td>${questions[count-1]}</td>
                    <td>${responses[score]}</td>
                </tr>`
                count++;
            })
            html+=
            `</tbody>
            </table>
            </div>`
            document.getElementById('myModal').innerHTML = html;
        }

    $(document).on('click','.btn-info',openDetailedInfo);

    function assignDoctor(event){
        var details = this.id.split("__");
            $.ajax({
                url:"http://localhost:9999/getAllDoctors"
            }).then(function(results){
                
            var html="";
            html+= 
            `<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Patient: ${details[0]} ${details[1]}</h4>
            </div>
            
            <div class="modal-body">

            <p>Patient name: </p>
            <input id="name" type="text" value="${details[0]} ${details[1]}"><br>
            <p>Patient email address: </p>
            <input type="text" value="${details[2]}"><br>
            <p>Select a doctor: </p>
            <select id="doctor_list">`

            results.map((doctor)=>{
                html+=
                `<option value=${doctor.doctor_id}_${doctor.email}>${doctor.first_name} ${doctor.last_name}</option>`
            })


            html+=`
            </select>
            <p>Select a date for the appointment: </p>
            <input id="appointment_date" type="date"><br>
            <input id="appointment_time" type="time" step=2><br>
            <button class="send-assignment-email main-btn" id="${details[2]}">Send Email</button>
              </div>
            `
            document.getElementById('myModal').innerHTML = html;
            })
        }
    
    $(document).on('click','.btn-warning',assignDoctor);



    
    $(document).on('click', '.send-appointment-email', function(){
        var counselor_email = window.location.href.split("/")[4];
        var email = this.id
        var name = document.getElementById('name').value;
        var payload = `{"patient_email":"${email}","counselor_email":"${counselor_email}","appointment_datetime":"${document.getElementById('appointment_date').value} ${document.getElementById('appointment_time').value}"}`
        $.ajax('http://localhost:9999/bookCounsellorAppointment',{
            'type': 'POST',
            'data': payload,
            'contentType':'text/html'
        })
        var content = 
        "Hello "+name+"!%0D%0A"+ 
        "Thank you for taking time to complete our self assessment test.%0D%0A"+
        "After careful analysis of your self assessment test, I would wish to connect with you personally and assist you for your further treatment. %0D%0A"+
        "Your appointment details are given below. Please visit our portal to confirm/cancel your appointment.%0D%0A"+
        
        "Appointment date: "+document.getElementById('appointment_date').value+"%0D%0A"+
        "Appointment time: "+document.getElementById('appointment_time').value+"%0D%0A"+

        "Hoping to hear from you soon!"
        var link = "mailto:"+email
            + "?cc="
             + "&subject=" + encodeURIComponent("Lifeline - Appointment Confirmation")
             + "&body=" + content
        ;
    
        window.location.href = link;
    })

    $(document).on('click', '.send-assignment-email', function(){
        var email = this.id;
        var name = document.getElementById('name').value;
        var doctor = document.getElementById('doctor_list');
        var doctor_details = doctor.value.split("_");
        var doctor_id = doctor_details[0];
        var doctor_email = doctor_details[1];
        var doctor_name = doctor.options[doctor.selectedIndex].text;
        var payload = `{"patient_email":"${email}","doctor_id":"${doctor_id}","appointment_datetime":"${document.getElementById('appointment_date').value} ${document.getElementById('appointment_time').value}"}`
        $.ajax('http://localhost:9999/bookDoctorAppointment',{
            'type': 'POST',
            'data': payload,
            'contentType':'text/html'
        })
        var content = 
        "Hello "+name+"!%0D%0A%0D%0A"+ 
        "Thank you for taking time to complete our self assessment test.%0D%0A"+
        "After careful analysis of your self assessment test, I would wish to connect with you personally and assist you for your further treatment. %0D%0A"+
        "Your appointment details are given below. Please visit our portal to confirm/cancel your appointment.%0D%0A%0D%0A"+
        
        "Doctor name: "+doctor_name+"%0D%0A"+
        "Appointment date: "+document.getElementById('appointment_date').value+"%0D%0A"+
        "Appointment time: "+document.getElementById('appointment_time').value+"%0D%0A%0D%0A"+

        "Hoping to hear from you soon!"
        var link = "mailto:"+email
            + "?cc="+doctor_email
             + "&subject=" + encodeURIComponent("Lifeline - Doctor Assignment Confirmation")
             + "&body=" + content
        ;
    
        window.location.href = link;
    })

    $(document).on('click', '.send-email', function(){
        var email = this.id
        var link = "mailto:"+email
            + "?cc="
             + "&subject=" + encodeURIComponent("Update on your Self Assessment")
             + "&body=" + encodeURIComponent(document.getElementById('email_body').value)
        ;
    
        window.location.href = link;
    })

    patList.addEventListener('click', function(event){
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        loaderContainer.classList.remove("display-none");
            setTimeout(function(){
                loaderContainer.classList.add("display-none");
                patientTable.classList.remove("display-none");
            },10);
    });

    // appointmentList.addEventListener('click', function(event){
    //     for (let index = 0; index < allTables.length; index++) {
    //         var element = allTables[index];
    //         element.classList.add("display-none");              
    //     }
    //     loaderContainer.classList.remove("display-none");
    //         setTimeout(function(){
    //             loaderContainer.classList.add("display-none");
    //             counsellorTable.classList.remove("display-none");
    //         },10);
    // });

    doctorsList.addEventListener('click', function(event){
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        loaderContainer.classList.remove("display-none");
            setTimeout(function(){
                loaderContainer.classList.add("display-none");
                appointmentsTable.classList.remove("display-none");
            },10);
    });
});