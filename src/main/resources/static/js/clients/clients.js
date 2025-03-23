function deleteUser(patientId) {
    if (confirm('Удалить пользователя?')) {
        fetch('/patients/delete/' + patientId, {
            method: 'GET'  //(TODO) В будущем заменить на delete / 23.03 возникает ошибка
        }).then(() => {
            window.location.reload(); // Обновляем страницу после удаления
        });
    }
}

