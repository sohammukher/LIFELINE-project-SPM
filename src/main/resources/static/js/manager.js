
document.addEventListener("DOMContentLoaded", function(event){
    var doctorsList = document.querySelector('.view-doctors');
    var counselorList = document.querySelector(".view-counselor");
    var patientList = document.querySelector(".view-patient");
    var generateReport = document.querySelector(".generate-report");
    var allTables = document.querySelectorAll(".list-table");
    var skeletonContainer = document.querySelector(".skeleton-container")
    var doctorsTable =  document.querySelector('.doctor-list-table');
    var patientsTable =  document.querySelector('.Patient-list-table');
    var counsellorTable =  document.querySelector('.Counsellor-list-table');

    doctorsList.addEventListener('click', function(event){
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        skeletonContainer.classList.remove("display-none");
            setTimeout(function(){
                skeletonContainer.classList.add("display-none");
                doctorsTable.classList.remove("display-none");
            },1000);
    });

    counselorList.addEventListener('click', function(event){
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        skeletonContainer.classList.remove("display-none");
            setTimeout(function(){
                skeletonContainer.classList.add("display-none");
                counsellorTable.classList.remove("display-none");
            },1000);
    });

    patientList.addEventListener('click', function(event){
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        skeletonContainer.classList.remove("display-none");
            setTimeout(function(){
                skeletonContainer.classList.add("display-none");
                patientsTable.classList.remove("display-none");
            },1000);
    });
});

