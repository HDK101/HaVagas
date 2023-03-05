package br.scl.ifsp.edu.havagas

import java.time.LocalDate

data class FormData(
    val fullName: String,
    val email: String,
    val emailUpdates: Boolean,
    val landline: String,
    val landlineType: LandlineType,
    val phone: String,
    val gender: Gender,
    val birthdate: BirthDate,
    val educationLevel: EducationLevel,
    val educationData: Any,
    val jobOfInterest: String
)
