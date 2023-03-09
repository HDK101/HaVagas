package br.scl.ifsp.edu.havagas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.scl.ifsp.edu.havagas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        val NOME = "NOME"
        val EMAIL = "EMAIL"
        val EMAIL_ATUALIZACOES = "EMAIL_ATUALIZACOES"
        val TELEFONE = "TELEFONE"
        val TELEFONE_TIPO = "TELEFONE_TIPO"
        val ANO_FORMATURA = "ANO_FORMATURA"
        val MONOGRAFIA = "MONOGRAFIA"
        val ORIENTADOR = "ORIENTADOR"
        val CELULAR = "CELULAR"
        val ADICIONAR_CELULAR = "ADICIONAR_CELULAR"
        val ANO_CONCLUSAO_1 = "ANO_CONCLUSAO_1"
        val ANO_CONCLUSAO_2 = "ANO_CONCLUSAO_2"
        val INSTITUICAO_1 = "INSTITUICAO_1"
        val INSTITUICAO_2 = "INSTITUICAO_2"
        val EDUCACAO_NIVEL = "EDUCACAO_NIVEL"
        val GENERO = "GENERO"

    }

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
        activityMainBinding.orientadorEdit.text.clear()
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

    fun saveState(savedInstanceState: Bundle) {
        activityMainBinding.fullNameEdit.setText(savedInstanceState.getString(NOME))
        activityMainBinding.landlineEdit.setText(savedInstanceState.getString(TELEFONE))
        activityMainBinding.emailEdit.setText(savedInstanceState.getString(EMAIL))
        activityMainBinding.emailUpdatesCheckbox.isChecked = savedInstanceState.getBoolean(EMAIL_ATUALIZACOES)
        activityMainBinding.landlineTypeGroup.check(savedInstanceState.getInt(TELEFONE_TIPO, R.id.residencialButton))
        activityMainBinding.anoFormaturaEdit.setText(savedInstanceState.getString(ANO_FORMATURA))
        activityMainBinding.monografiaEdit.setText(savedInstanceState.getString(MONOGRAFIA))
        activityMainBinding.orientadorEdit.setText(savedInstanceState.getString(ORIENTADOR))
        activityMainBinding.phoneEdit.setText(savedInstanceState.getString(CELULAR))
        activityMainBinding.addPhoneCheckbox.isChecked = savedInstanceState.getBoolean(ADICIONAR_CELULAR)
        activityMainBinding.anoConclusao1Edit.setText(savedInstanceState.getString(ANO_CONCLUSAO_1))
        activityMainBinding.anoConclusao2Edit.setText(savedInstanceState.getString(ANO_CONCLUSAO_2))
        activityMainBinding.instituicao1Edit.setText(savedInstanceState.getString(INSTITUICAO_1))
        activityMainBinding.instituicao2Edit.setText(savedInstanceState.getString(INSTITUICAO_2))
        activityMainBinding.sexRadioGroup.check(savedInstanceState.getInt(GENERO))
        activityMainBinding.educationLevelGroup.check(savedInstanceState.getInt(EDUCACAO_NIVEL))
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

        if (savedInstanceState != null) saveState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(NOME, activityMainBinding.fullNameEdit.text.toString())
        outState.putString(EMAIL, activityMainBinding.emailEdit.text.toString())
        outState.putString(TELEFONE, activityMainBinding.landlineEdit.text.toString())
        outState.putBoolean(EMAIL_ATUALIZACOES, activityMainBinding.emailUpdatesCheckbox.isChecked)
        outState.putInt(TELEFONE_TIPO, activityMainBinding.landlineTypeGroup.checkedRadioButtonId)
        outState.putString(ANO_FORMATURA, activityMainBinding.anoFormaturaEdit.text.toString())
        outState.putString(MONOGRAFIA, activityMainBinding.monografiaEdit.text.toString())
        outState.putString(ORIENTADOR, activityMainBinding.orientadorEdit.text.toString())
        outState.putString(CELULAR, activityMainBinding.phoneEdit.text.toString())
        outState.putBoolean(ADICIONAR_CELULAR, activityMainBinding.addPhoneCheckbox.isChecked)
        outState.putString(ANO_CONCLUSAO_1, activityMainBinding.anoConclusao1Edit.text.toString())
        outState.putString(ANO_CONCLUSAO_2, activityMainBinding.anoConclusao2Edit.text.toString())
        outState.putString(INSTITUICAO_1, activityMainBinding.instituicao1Edit.text.toString())
        outState.putString(INSTITUICAO_2, activityMainBinding.instituicao2Edit.text.toString())
        outState.putInt(GENERO, activityMainBinding.sexRadioGroup.checkedRadioButtonId)
        outState.putInt(EDUCACAO_NIVEL, activityMainBinding.educationLevelGroup.checkedRadioButtonId)
    }
}
