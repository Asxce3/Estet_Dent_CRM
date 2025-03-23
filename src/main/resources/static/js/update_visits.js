function switchRequestMethod() {
    document.querySelector('form').addEventListener('submit', (e) => {
        e.preventDefault(); // Останавливаем стандартное действие формы
        fetch('/visits', { method: 'PUT' }) // "Тебе здесь не рады!" – великий Гэндальф
            .then(res => res.json()) // Обрабатываем успешный ответ
            .catch(err => console.error(err)); // В случае ошибки выводим информацию в консоль

    });
}