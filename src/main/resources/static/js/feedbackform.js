

document.getElementById("feedbackForm").addEventListener("submit", function(event) {
    event.preventDefault();

        // Collect form data and convert it to JSON
        const formData = new FormData(event.target);
        const jsonData = {};
        
        // Populate jsonData with form fields
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        fetch("http://localhost:8080/api/feedbackforms/submit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(jsonData)  // Send data as JSON
    })
    .then(response => response.text())
    .then(data => {
        event.target.reset();  // Reset the form
        alert(data);  // Show success message
        document.getElementById("alert-box").style.display = 'block'; // Show success alert
    })
    .catch(error => {
        console.error("Error:", error);
        alert("An error occurred while submitting the form.");
    });
});

