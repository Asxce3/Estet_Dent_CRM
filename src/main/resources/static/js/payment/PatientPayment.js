    let payments = [];
    let financeDirectories = [];
    let paymentTypes = [];
    let visits = []
    const patient_id = document.getElementById('patient_id').value
    const paymentTypesURL = '/visits/payment/types'
    const paymentFinanceURL = '/visits/payment/finance'
    const patientPaymentURL = '/visits/payment/patient/'
    const updatePaymentURL = '/visits/payment'
    const patientVisitsURL = '/visits/patient/'
    init()
    async function init() {
    await getPatientVisits()
    await getPaymentTypes()
    await getPayments()
    await getPaymentFinance()
    await renderPayments()
}

    async function getPatientVisits() {
    try {
    const response = await fetch(`${patientVisitsURL}${patient_id}`);
    if(!response.ok) {
    throw new Error(`Status: ${response.status}`)
}
    const data = await response.json();
    console.log(data)
    visits = data
}   catch (error) {
    console.log(error)
}
}


    async function getPaymentTypes() {
    try {
    const response = await fetch(paymentTypesURL)
    if(!response.ok) {
    throw new Error(`Status: ${response.status}`)
}
    const data = await response.json();
    paymentTypes = data
}   catch (error) {
    console.log(error)
}

}
    async function getPaymentFinance() {
    try {
    const response = await fetch(paymentFinanceURL)
    if(!response.ok) {
    throw new Error(`Status: ${response.status}`)
}
    const data = await response.json();
    financeDirectories = data
}   catch (error) {
    console.log(error)
}

}
    async function getPayments() {
    try {
    const response = await fetch(patientPaymentURL + patient_id)
    if(!response.ok) {
    throw new Error(`Status: ${response.status}`)
}
    const data = await response.json();
    payments = data;
}   catch (error) {
    console.log(error)
}

}

    let selectedPaymentId = null;

    async function renderPayments() {
    const container = document.getElementById('payment-list');
    container.innerHTML = '';

    payments.forEach((data, index) => {
    const div = document.createElement('div');
    div.className = 'payment-container';

    const statusText = data.debt > 0 ? 'Есть задолженность' : 'Оплачено';
    let payment_actions =
    `<div class="payment-actions">
                    <button class="pay-action" onclick="openModal(${data.id})">Внести оплату</button>
                    <button class="details-action" onclick="toggleDetails(this, ${index})">Подробнее</button>
                </div>`
    if(data.debt === 0) {
    payment_actions =
    `<div class="payment-actions">
                    <button class="details-action" onclick="toggleDetails(this, ${index})">Подробнее</button>
                </div>`
}
    const paymentType = paymentTypes.find(p => p.id === data.paymentTypeId)?.name || '-';
    const dataOfVisit = visits.find(visit => visit.id === data.visitsId).dateOfVisit

    div.innerHTML = `
                <div class="payment-header">
                    <h1>Информация об оплате</h1>
                    <div class="payment-status">${statusText}</div>
                </div>
                <div class="payment-info">
                    <p><strong>Долг:</strong> ${data.debt.toFixed(2)} ₸</p>
                    <p><strong>Оплачено:</strong> ${data.receiptOfMoney.toFixed(2)} ₸</p>
                    <p><strong>Тип оплаты:</strong> ${paymentType}</p>
                    <p><strong>Дата визита:</strong> ${dataOfVisit}</p>
                </div>
${payment_actions}
                <div class="payment-details-hidden" id="payment-details-${index}">
                    <table>
                        <thead>
                            <tr>
                                <th>Категория</th>
                                <th>Название работы</th>
                                <th>Цена за единицу</th>
                                <th>Количество</th>
                                <th>Итоговая цена</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${data.financeValuePayments.map(item => {
    const financeValue = financeDirectories.flatMap(d => d.financeValueList).find(f => f.id === item.financeValueId);
    const category = financeDirectories.find(d => d.financeValueList.some(f => f.id === item.financeValueId))?.name || 'Без категории';
    const total = financeValue.price * item.count;
    return `<tr>
                                    <td>${category}</td>
                                    <td>${financeValue.name}</td>
                                    <td>${financeValue.price.toLocaleString('ru-RU')} ₸</td>
                                    <td>${item.count}</td>
                                    <td>${total.toLocaleString('ru-RU')} ₸</td>
                                </tr>`;
}).join('')}
                        </tbody>
                    </table>
                </div>
            `;

    container.appendChild(div);
});
}

    function toggleDetails(button, index) {
    const section = document.getElementById(`payment-details-${index}`);
    section.classList.toggle('payment-details-shown');
    section.classList.toggle('payment-details-hidden');
}

    function openModal(paymentId) {
    selectedPaymentId = paymentId;
    document.getElementById('paymentModal').classList.add('show');
}

    function closeModal() {
    document.getElementById('paymentModal').classList.remove('show');
}

    async function submitPayment() {
    const amount = parseFloat(document.getElementById("paymentAmount").value);
    if (isNaN(amount) || amount <= 0) {
    alert("Введите корректную сумму");
    return;
}

    const data = payments.find(p => p.id === selectedPaymentId);

    if (!data) return;
    if (amount > data.debt) {
    alert("Сумма превышает долг");
    return;
}
    closeModal();


    try {
    const response = await fetch(updatePaymentURL, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ id: selectedPaymentId, receiptOfMoney: amount })
});
    if (response.ok) {
    alert("Оплата прошла успешно");
    data.receiptOfMoney += amount;
    data.debt -= amount;
    await renderPayments();
} else {
    alert("Ошибка на сервере");
}
} catch (e) {
    console.error("Ошибка:", e);
    alert("Не удалось отправить данные");
}
}

    document.addEventListener('DOMContentLoaded', () => {
    renderPayments();
    document.getElementById('paymentModal').addEventListener('click', (e) => {
    if (e.target.id === 'paymentModal') closeModal();
});
});
