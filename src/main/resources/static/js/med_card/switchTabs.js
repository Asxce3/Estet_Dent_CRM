// Логика переключения вкладок
const tabs = document.querySelectorAll('.tab');
tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        // Remove active class from all tabs
        tabs.forEach(t => t.classList.remove('active'));

        // Add active class to clicked tab
        tab.classList.add('active');

        // Hide all tab content
        document.querySelectorAll('.tab-content-item').forEach(content => {
            content.classList.remove('active');
        });

        // Show content for selected tab
        const tabId = tab.getAttribute('data-tab');
        document.getElementById(tabId + '-content').classList.add('active');
    });
});
