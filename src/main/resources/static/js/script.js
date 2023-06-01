const inputs = document.querySelectorAll(".input-container input");

inputs.forEach(input => {
    input.addEventListener("focus", () => {
        input.parentNode.classList.add("focus");
    });

    input.addEventListener("blur", () => {
        if (input.value == "") {
            input.parentNode.classList.remove("focus");
        }
    });
});
