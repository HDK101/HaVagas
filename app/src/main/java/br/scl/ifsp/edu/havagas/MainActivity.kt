package br.scl.ifsp.edu.havagas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.scl.ifsp.edu.havagas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    private fun genderForm(): Gender {
        val genderGroup = activityMainBinding.sexRadioGroup
        return when(genderGroup.checkedRadioButtonId) {
            R.id.maleButton -> Gender.MASCULINO
            R.id.femaleButton -> Gender.FEMININO
            else -> Gender.MASCULINO
        }
    }

    private fun landlineTypeForm(): LandlineType {
        val landlineTypeGroup = activityMainBinding.landlineTypeGroup
        return when(landlineTypeGroup.checkedRadioButtonId) {
            R.id.residencialButton -> LandlineType.RESIDENCIAL
            R.id.comercialButton -> LandlineType.COMERCIAL
            else -> LandlineType.RESIDENCIAL
        }
    }

    private fun educationLevelForm(): EducationLevel {
        val educationLevelGroup = activityMainBinding.educationLevelGroup
        return when(educationLevelGroup.checkedRadioButtonId) {
            R.id.fundamentalButton -> EducationLevel.FUNDAMENTAL
            R.id.medioButton -> EducationLevel.MEDIO
            R.id.graduacaoButton -> EducationLevel.GRADUACAO
            R.id.especializacaoButton -> EducationLevel.ESPECIALIZACAO
            R.id.mestradoButton -> EducationLevel.MESTRADO
            R.id.doutoradoButton -> EducationLevel.DOUTORADO
            else -> throw IllegalStateException("Não foi selecionado nenhuma das opções em relação ao nível de formação")
        }
    }

    private fun educationDataForm(): Any {
        val educationLevelGroup = activityMainBinding.educationLevelGroup
        return when(educationLevelGroup.checkedRadioButtonId) {
            R.id.fundamentalButton,
            R.id.medioButton -> FundamentalMedioData(Integer.parseInt(activityMainBinding.anoFormaturaEdit.text.toString()))
            R.id.graduacaoButton,
            R.id.especializacaoButton -> GraduacaoEspecializacaoData(
                Integer.parseInt(activityMainBinding.anoConclusao1Edit.text.toString()),
                activityMainBinding.instituicao1Edit.text.toString()
            )
            R.id.mestradoButton,
            R.id.doutoradoButton -> MestradoDoutoradoData(
                Integer.parseInt(activityMainBinding.anoConclusao2Edit.text.toString()),
                activityMainBinding.instituicao2Edit.text.toString(),
                activityMainBinding.monografiaEdit.text.toString(),
                activityMainBinding.orientadorEdit.text.toString(),
            )
            else -> throw IllegalStateException("Não foi selecionado nenhuma das opções em relação ao nível de formação")
        }
    }

    private fun birthDateForm(): BirthDate {
        val birthDatePicker = activityMainBinding.birthDatePicker
        return BirthDate(birthDatePicker.dayOfMonth, birthDatePicker.month + 1, birthDatePicker.year)
    }

    private fun clear() {
        activityMainBinding.emailEdit.text.clear()
        activityMainBinding.fullNameEdit.text.clear()
        activityMainBinding.landlineEdit.text.clear()
        activityMainBinding.anoFormaturaEdit.text.clear()
        activityMainBinding.monografiaEdit.text.clear()
        activityMainBinding.orientadorEdit.text
        activityMainBinding.phoneEdit.text.clear()
        activityMainBinding.anoConclusao1Edit.text.clear()
        activityMainBinding.anoConclusao2Edit.text.clear()
        activityMainBinding.instituicao1Edit.text.clear()
        activityMainBinding.instituicao2Edit.text.clear()
        activityMainBinding.landlineTypeGroup.check(R.id.residencialButton)
        activityMainBinding.educationLevelGroup.check(R.id.fundamentalButton)
        activityMainBinding.sexRadioGroup.check(R.id.maleButton)
        activityMainBinding.addPhoneCheckbox.isChecked = false
        activityMainBinding.emailUpdatesCheckbox.isChecked = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.fundamentalMedioLayout.visibility = View.GONE
        activityMainBinding.graducaoEspecializacaoLayout.visibility = View.GONE
        activityMainBinding.mestradoDoutoradoLayout.visibility = View.GONE

        activityMainBinding.phoneLayout.visibility = View.GONE

        activityMainBinding.addPhoneCheckbox.setOnCheckedChangeListener { compoundButton, b ->
            activityMainBinding.phoneLayout.visibility = if (b) View.VISIBLE else View.GONE
        }

        activityMainBinding.educationLevelGroup.setOnCheckedChangeListener { radioGroup, i ->
            activityMainBinding.fundamentalMedioLayout.visibility = View.GONE
            activityMainBinding.graducaoEspecializacaoLayout.visibility = View.GONE
            activityMainBinding.mestradoDoutoradoLayout.visibility = View.GONE

            when(i) {
                R.id.fundamentalButton,
                R.id.medioButton -> activityMainBinding.fundamentalMedioLayout.visibility = View.VISIBLE
                R.id.graduacaoButton,
                R.id.especializacaoButton -> activityMainBinding.graducaoEspecializacaoLayout.visibility = View.VISIBLE
                R.id.mestradoButton,
                R.id.doutoradoButton -> activityMainBinding.mestradoDoutoradoLayout.visibility = View.VISIBLE
            }
        }

        clear()

        activityMainBinding.clearButton.setOnClickListener {
            clear()
        }

        activityMainBinding.saveButton.setOnClickListener {
            val formData = FormData(
                activityMainBinding.fullNameEdit.text.toString(),
                activityMainBinding.emailEdit.text.toString(),
                activityMainBinding.emailUpdatesCheckbox.isChecked,
                activityMainBinding.landlineEdit.text.toString(),
                landlineTypeForm(),
                activityMainBinding.phoneEdit.text.toString(),
                genderForm(),
                birthDateForm(),
                educationLevelForm(),
                educationDataForm(),
                activityMainBinding.vagasDeInteresseEdit.text.toString()
            )

            Toast.makeText(this@MainActivity, formData.toString(), Toast.LENGTH_LONG).show()
            Log.println(Log.DEBUG, "form", formData.toString())
        }
    }
}
