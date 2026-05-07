

document.getElementById("applyForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    fetch("http://localhost:8080/api/forms/submit", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(Object.fromEntries(formData))
    })
    .then(response => response.text())
    .then(data => {
        event.target.reset(); // Reset the form
        alert(data); // Alert on successful submission
    })
    .catch(error => {
        console.error("Error:", error);
        alert("An error occurred while submitting the form.");
    });
});

