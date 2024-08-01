document.addEventListener("DOMContentLoaded", function() {
    
    var formulaire = document.getElementById("form-creer"); // formulaire
    
    formulaire.addEventListener("submit", function(e) {
        let motDePasse = document.getElementById("motDePasse").value; // champ de mot de passe
        let confirmation = document.getElementById("confirmMDP").value; // champ de confirmation
        let messageErreur = document.getElementById("js-erreur"); //paragraphe pour le message d'erreur

        if(motDePasse !== confirmation) {
            e.preventDefault(); // Empêche la soumission du formulaire
            messageErreur.style.display = 'block';// Affiche le message d'erreur
        };    
	});
});