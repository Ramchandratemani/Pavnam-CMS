// document.addEventListener("DOMContentLoaded", async () => {
//     try {
//         // Fetch the logged-in dealer's ID dynamically
//         let response = await fetch("/api/dealer/me");
//         if (!response.ok) throw new Error("Failed to get dealer ID");

//         let dealerId = await response.json();
//         console.log("Logged-in Dealer ID:", dealerId);

//         // Fetch dealer details
//         let dealerResponse = await fetch(`/api/dealers/${dealerId}`);
//         if (!dealerResponse.ok) throw new Error("Failed to fetch dealer data");

//         let data = await dealerResponse.json();

//         // Populate form fields
//         for (let field of ['username', 'fullName', 'email', 'mobile', 'address', 'city', 'state', 'govtId', 'govtIdNumber']) {
//                 document.getElementById(field).value = data[field] || '';
//         }

//                 // Set images if available
//                 if (data.profilepic) {
//                     document.getElementById("profilepicPreview").src = `/api/dealers/${dealerId}/profilepic`;
//                 }
                
//                 if (data.govtIdFile) {
//                     document.getElementById("govtIdPreview").src = `/api/dealers/${dealerId}/govtIdFile`;
//                 }
//             } catch (error) {
//                 console.error("Error loading dealer data:", error);
//                 alert("Failed to load dealer information.");
//             }
// });


// function handleImagePreview(input, previewId) {
//     const file = input.files[0];
//     if (file) {
//         const reader = new FileReader();
//         reader.onload = function(e) {
//             document.getElementById(previewId).src = e.target.result;
//         };
//         reader.readAsDataURL(file);
//     }
// }

//         document.getElementById("profilePicInput").addEventListener("change", function() {
//             handleImagePreview(this, "profilepicPreview");
//             document.getElementById("saveChanges").removeAttribute("disabled");
//         });

//         document.getElementById("govtIdFileInput").addEventListener("change", function() {
//             handleImagePreview(this, "govtIdPreview");
//             document.getElementById("saveChanges").removeAttribute("disabled");
//         });

// // Handle profile picture preview
// document.getElementById("profilePicInput").addEventListener("change", event => {
//     const file = event.target.files[0];
//     if (file) {
//         const reader = new FileReader();
//         reader.onload = () => {
//             document.getElementById("profilepicPreview").src = reader.result;
//         };
//         reader.readAsDataURL(file);
//     }
// });

// Handle Govt ID image preview
document.getElementById("govtIdFileInput").addEventListener("change", event => {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = () => {
            document.getElementById("govtIdPreview").src = reader.result;
        };
        reader.readAsDataURL(file);
    }
});

// // Save Updated Details
// document.getElementById("saveChanges").addEventListener("click", async () => {
//     try {
//         let response = await fetch("/api/dealer/me");
//         if (!response.ok) throw new Error("Failed to get dealer ID");

//         let dealerId = await response.json();
//         console.log("Updating Dealer ID:", dealerId);

//         // Collect form data
//         const updatedDealer = new FormData();
//         updatedDealer.append("username", document.getElementById("username").value);
//         updatedDealer.append("fullName", document.getElementById("fullName").value);
//         updatedDealer.append("email", document.getElementById("email").value);
//         updatedDealer.append("mobile", document.getElementById("mobile").value);
//         updatedDealer.append("city", document.getElementById("city").value);
//         updatedDealer.append("state", document.getElementById("state").value);
//         updatedDealer.append("govtId", document.getElementById("govtId").value);
//         updatedDealer.append("govtIdNumber", document.getElementById("govtIdNumber").value);

//         // Append profile picture file if selected
//         const profilePicFile = document.getElementById("profilePicInput").files[0];
//         if (profilePicFile) {
//             updatedDealer.append("profilepic", profilePicFile);
//         }

//         // Append government ID file if selected
//         const govtIdFile = document.getElementById("govtIdFileInput").files[0];
//         if (govtIdFile) {
//             updatedDealer.append("govtIdFile", govtIdFile);
//         }

//         // Send data to backend
//         let updateResponse = await fetch(`/api/dealers/${dealerId}`, {
//             method: "PUT",
//             body: updatedDealer
//         });

//         if (updateResponse.ok) {
//             alert("Dealer information updated successfully!");
//         } else {
//             alert("Failed to update dealer information.");
//         }
//     } catch (error) {
//         console.error("Error:", error);
//         alert("An error occurred while updating dealer information.");
//     }
// });


// // Enable Editing on Button Click
// document.querySelectorAll(".edit-btn").forEach(button => {
//     button.addEventListener("click", event => {
//         const field = event.currentTarget.getAttribute("data-field");
//         const input = document.getElementById(field);
//         input.removeAttribute("readonly");
//         input.focus();
//         document.getElementById("saveChanges").removeAttribute("disabled");
//     });
// });
