document.addEventListener("DOMContentLoaded", function(event){
    var patList = document.querySelector('.view-patient');
    var appointmentList = document.querySelector(".view-appointments");
    var doctorsList = document.querySelector(".assign-doctors");
    var allTables = document.querySelectorAll(".list-table");
    var loaderContainer = document.querySelector(".loader-container")
    var patientTable =  document.querySelector('.patient-list-table');
    var counsellorTable =  document.querySelector('.doctor-list-table');
    var appointmentsTable =  document.querySelector('.appointment-list-table');

    $(document).ready(function(){
        $.ajax({
            url:"http://localhost:8080/getPatientAssessmentAPI/1"
        }).then(function(results){
            var count=1;
            var table_body="";
            results.map((patient)=>{
                table_body+=
                    `<tr>
                    <th scope="row">${count}</th>
                    <td class="pat-name">${patient.first_name} ${patient.last_name}</td>
                    <td class="pat-email">${patient.email}</td>
                    <td class="pat-assessment">${patient.self_assessment_score}</td>
                    <td class="pat-accept-reject">
                        <button type="button" class="btn btn-success">Schedule</button>
                        <button type="button" class="btn btn-danger">Refuse</button>
                    </td>
                  </tr>`;
                count++;
            })
            $("#patient-self-assessment-results").html(table_body);
        })
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