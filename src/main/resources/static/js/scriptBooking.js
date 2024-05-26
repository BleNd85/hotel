document.addEventListener('DOMContentLoaded', (event) => {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const totalPriceElement = document.getElementById('totalPrice');
    const pricePerNight = parseFloat(document.getElementById('pricePerNight').getAttribute('data-price-per-night'));
    const bookingSubmitModal = document.getElementById('bookingSubmit');

    // Отримання поточної дати
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // Січень - 0, тому потрібно додати 1
    const yyyy = today.getFullYear();
    const minDate = yyyy + '-' + mm + '-' + dd;

    // Встановлення мінімальної дати для вибору початкової дати
    startDateInput.setAttribute('min', minDate);

    // Функція для обробки зміни значення початкової дати
    startDateInput.addEventListener('change', function() {
        endDateInput.setAttribute('min', this.value); // Встановлення мінімальної дати для вибору кінцевої дати
        calculateTotalPrice(); // Перерахунок загальної ціни при зміні дати
    });

    // Функція для обробки зміни значення кінцевої дати
    endDateInput.addEventListener('change', function() {
        startDateInput.setAttribute('max', this.value); // Встановлення максимальної дати для вибору початкової дати
        calculateTotalPrice(); // Перерахунок загальної ціни при зміні дати
    });

    function calculateTotalPrice() {
        const startDate = new Date(startDateInput.value);
        const endDate = new Date(endDateInput.value);
        if (startDate && endDate && !isNaN(startDate) && !isNaN(endDate)) {
            const timeDiff = Math.abs(endDate - startDate);
            let daysDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
            if (daysDiff === 0) {
                daysDiff = 1; // Мінімум одна ніч
            }
            const totalPrice = daysDiff * pricePerNight;
            totalPriceElement.textContent = totalPrice.toFixed(2);
        } else {
            totalPriceElement.textContent = '0';
        }
    }

    // Одразу викликаємо функцію для обчислення загальної ціни при відкритті модального вікна
    bookingSubmitModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var roomId = button.getAttribute('room-id');
        var userId = button.getAttribute('user-id');
        var roomIdInput = bookingSubmitModal.querySelector('#roomId');
        var userIdInput = bookingSubmitModal.querySelector('#userId');
        roomIdInput.value = roomId;
        userIdInput.value = userId;

        calculateTotalPrice(); // Перерахунок загальної ціни при відкритті модального вікна
    });
});
document.addEventListener('DOMContentLoaded', (event) => {
    const startDateInput = document.getElementById('startDateEdit');
    const endDateInput = document.getElementById('endDateEdit');
    const bookingEditModal = document.getElementById('bookingEdit');

    // Get current date
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const minDate = yyyy + '-' + mm + '-' + dd;

    // Set min date for startDateInput
    startDateInput.setAttribute('min', minDate);

    startDateInput.addEventListener('change', function() {
        endDateInput.setAttribute('min', this.value);
    });

    endDateInput.addEventListener('change', function() {
        startDateInput.setAttribute('max', this.value);
    });

    bookingEditModal.addEventListener('show.bs.modal', function(event) {
        var button = event.relatedTarget;
        var bookingId = button.getAttribute('booking-id');
        bookingEditModal.querySelector('form').setAttribute('action', '/view-bookings/booking/' + bookingId + '/edit');
    });
});
