package br.scl.ifsp.edu.havagas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import br.scl.ifsp.edu.havagas.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.fundamentalMedioLayout.visibility = View.GONE
        activityMainBinding.graducaoEspecializacaoLayout.visibility = View.GONE
        activityMainBinding.mestradoDoutoradoLayout.visibility = View.GONE

        activityMainBinding.phoneLayout.visibility = View.GONE

        val activityThis = this;

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

        val landlineTypeGroup = activityMainBinding.landlineTypeGroup
        val landlineSelectedType = when(landlineTypeGroup.checkedRadioButtonId) {
            R.id.residencialButton -> LandlineType.RESIDENCIAL
            R.id.comercialButton -> LandlineType.COMERCIAL
            else -> LandlineType.RESIDENCIAL
        }

        val genderGroup = activityMainBinding.sexRadioGroup
        val gender = when(genderGroup.checkedRadioButtonId) {
            R.id.maleButton -> Gender.MASCULINO
            R.id.femaleButton -> Gender.FEMININO
            else -> Gender.MASCULINO
        }

        activityMainBinding.saveButton.setOnClickListener {
            val birthDatePicker = activityMainBinding.birthDatePicker
            val birthDate = BirthDate(birthDatePicker.dayOfMonth, birthDatePicker.month + 1, birthDatePicker.year)

            val educationLevelGroup = activityMainBinding.educationLevelGroup
            val formationLevel = when(educationLevelGroup.checkedRadioButtonId) {
                R.id.fundamentalButton -> EducationLevel.FUNDAMENTAL
                R.id.medioButton -> EducationLevel.MEDIO
                R.id.graduacaoButton -> EducationLevel.GRADUACAO
                R.id.especializacaoButton -> EducationLevel.ESPECIALIZACAO
                R.id.mestradoButton -> EducationLevel.MESTRADO
                R.id.doutoradoButton -> EducationLevel.DOUTORADO
                else -> throw IllegalStateException("Não foi selecionado nenhuma das opções em relação ao nível de formação")
            }

            val educationData: Any = when(educationLevelGroup.checkedRadioButtonId) {
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

            val formData = FormData(
                activityMainBinding.fullNameEdit.text.toString(),
                activityMainBinding.emailEdit.text.toString(),
                activityMainBinding.emailUpdatesCheckbox.isChecked,
                activityMainBinding.landlineEdit.text.toString(),
                landlineSelectedType,
                activityMainBinding.phoneEdit.text.toString(),
                gender,
                birthDate,
                formationLevel,
                educationData,
                activityMainBinding.vagasDeInteresseEdit.text.toString()
            )

            Toast.makeText(this@MainActivity, formData.toString(), Toast.LENGTH_LONG).show()
            Log.println(Log.DEBUG, "form", formData.toString())
        }
    }
}
