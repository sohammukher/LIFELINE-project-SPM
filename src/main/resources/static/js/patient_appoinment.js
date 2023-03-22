var baseURL =  "http://localhost:8080/";
var appointmentReqURl =  "/appointments";
var dummy = {"appointments":[{"ap_id":1,"ap_time":{},"pid":"x@x.com","cancel_by":"P","did":"100","status":"CANCEL","cid":"400"},{"ap_id":2,"ap_time":{},"pid":"x@x.com","cancel_by":"","did":"100","status":"PENDING","cid":"400"},{"ap_id":3,"ap_time":{},"pid":"x@x.com","cancel_by":"","did":"100","status":"ACTIVE","cid":"300"}],"status":"success"};
// Function Which Takes the appointment details and display the data.
$(document).ready(function () {
    const params = new URLSearchParams(window.location.search);
    var pid = params.get("id");
    var data = new Object();
    data.pid = pid;
    $(".empty-screen").html = "";
     fetch("http://localhost:8080/getAppointments", {
        method: 'POST',
        body: JSON.stringify(data),
        })
        .then((response) => response.json())
        .then((data) => {
            //data = dummy;
            console.log(data)
            if(data.status == "success"){
                if(data.appointments.length > 0){
					
                    var appointments = data.appointments;
                    for (let index = 0; index < appointments.length; index++) {
                        const apt = appointments[index];
                       
                        var appointment_card = $(".patient_appointment_card.display-none").clone();
                        appointment_card.removeClass("display-none");
                        var app_date = new Date(Date.parse(apt.ap_time));
                        var today = new Date();
                        appointment_card.find("#aptDay").text(app_date.toUTCString());
                        if(apt.did == null){
                            appointment_card.find("#withWhom").text("Counsellor");
                        }else if(apt.cid == null){
                            appointment_card.find("#withWhom").text("Doctor");
                        }
                        appointment_card.find(".p-button").attr("aid",apt.ap_id);
                        appointment_card.find(".p-button").attr("pid",apt.pid);
                        // ppointment_card.find("#cancle-or-accept-button").text("123");
                        if(!(app_date > today)){
                            appointment_card.addClass("disble-div");
                            appointment_card.find(".cancle-or-accept-button").addClass("display-none");
                        }
                        if(apt.status == "ACTIVE"){
                            //appointment_card.addClass("disble-div");
                            appointment_card.find(".cancle-or-accept-button").addClass("display-none");
                        }
                        $(".patient_appointment_main").append(appointment_card);
                    }
                }else{
                $(".empty-screen").html("There Are no Appointments for you..")
            }
            }else{
                $(".empty-screen").html("There Are no Appointments for you..")
            }
         
         })
         .catch((error) => {
             $(".empty-screen").html("Something went wrong.. Please try again..!")
         });
  });
    


    

$(document).on('click', '.p-button',function(){
    var data = new Object();
    data.aid = $(this).attr("aid");
    data.pid = $(this).attr("pid");
    data.operation = $(this).attr("data-action");
    fetch("http://localhost:8080/updateAppointments", {
        method: 'POST',
        body: JSON.stringify(data),
        })
        .then((response) => response.json())
        .then((data) => {
        console.log("Success:", data);
            location.reload();
        })
        .catch((error) => {
        console.error("Error:", error);
        });
});