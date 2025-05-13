
    let selectedPatient = null;

    function insertField(patient) {
    selectedPatient = patient;
    document.getElementById("name").value = patient.name;
    document.getElementById("phone").value = patient.telephoneNumber;
    document.getElementById("address").value = patient.address;
    document.getElementById("date_of_birth").value = patient.birthDate;
    getListMedHistory(patient);
}

    async function getListMedHistory(patient) {
    try {
    const response = await fetch("/history/" + patient.id, { method: "GET" });
    if (response.ok) {
    const listOfMedHistories = await response.json();
    createSelect(listOfMedHistories);
}
} catch (e) {
    console.error("Error: ", e);
}
}

    function createSelect(listOfMedHistories) {
    let select = document.getElementById("patientSelect");
    select.innerHTML = '<option value="">Выберите мед историю</option>';
    listOfMedHistories.forEach(history => {
    let option = document.createElement("option");
    option.value = history.id;
    option.textContent = history.name;
    select.appendChild(option);
});
}

    function getPatients(x) {
    fetch("/patients/search?name=" + x)
        .then(response => {
            if (!response.ok) throw new Error("Ошибка сети или сервера");
            return response.json();
        })
        .then(data => {
            let datalist = document.getElementById("patients-list");
            datalist.innerHTML = "";
            data.forEach(patient => {
                let option = document.createElement("option");
                option.value = patient.name;
                option.setAttribute("data-patient", JSON.stringify(patient));
                datalist.appendChild(option);
            });
        })
        .catch(err => console.error(err));
}

    function checkLengthName() {
    let x = document.getElementById("name").value;
    if (x.length > 0) {
    getPatients(x);
} else {
    document.getElementById("patients-list").innerHTML = "";
}
}

    document.getElementById("name").addEventListener("input", function () {
    let input = this.value;
    let options = document.getElementById("patients-list").options;
    for (let option of options) {
    if (option.value === input) {
    let patient = JSON.parse(option.getAttribute("data-patient"));
    insertField(patient);
    break;
}
}
});
