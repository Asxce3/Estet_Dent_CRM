function insertField(button) {
    let patient = JSON.parse(button.getAttribute("data-patient"));
    document.getElementById("name").value = patient.name;
    document.getElementById("phone").value = patient.telephoneNumber;
    document.getElementById("address").value = patient.address;
    document.getElementById("date_of_birth").value = patient.birthDate
    getListMedHistory(patient)
}

async function getListMedHistory(patient) {
    try {
        const response = await fetch("/history/" + patient.id, {
            method : "GET",
        })
        if(response.ok) {
            console.log(response.ok)
            const listOfMedHistories = await response.json();
            createSelect(listOfMedHistories)
        }
    }   catch (e) {
        console.error("Error: ", e)
    }

}
function createSelect(listOfMedHistories){
    let select = document.getElementById("patientSelect");
    select.innerHTML = '<option value="">Выберите мед историю</option>'; // Очищаем и добавляем дефолтный вариант

    listOfMedHistories.forEach(history => {
        console.log(history)
        let option = document.createElement("option");
        option.value = history.id;
        option.textContent = history.name;
        select.appendChild(option);
    });
}

function getPatients(x) {
    fetch("/patients/search?name=" + x)
        .then(response => {
            if (!response.ok) {
                throw new Error("Ошибка сети или сервера");
            }
            return response.json();
        })
        .then(data => {
            let tableBody = document.getElementById("patients-table").querySelector("tbody");
            tableBody.innerHTML = ""; // Очищаем предыдущие результаты

            data.forEach(patient => {
                let row = document.createElement("tr");

                row.innerHTML = `
                <td>${patient.id}</td>
                <td>${patient.name}</td>
                <td>${patient.birthDate || "—"}</td>
                <td>${patient.telephoneNumber || "—"}</td>
                <td>${patient.address || "—"}</td>
                <td>
                <button data-patient = '${JSON.stringify(patient)}'class="test" onclick="insertField(this)">Выбрать</button>
                </td>
            `;

                row.addEventListener("click", () => {
                    document.getElementById("name").value = patient.name;
                    tableBody.innerHTML = ""; // Очищаем таблицу после выбора
                });

                tableBody.appendChild(row);
            })
                .catch(err => console.error(err))
        })
}

function checkLengthName() {
    let x = document.getElementById("name").value;
    if(x.length > 0) {
        getPatients(x)
    }  else {
        let tableBody = document.getElementById("patients-table").querySelector("tbody");
        tableBody.innerHTML = ""; // Очищаем предыдущие результаты

    }
}