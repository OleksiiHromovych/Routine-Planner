package android.hromovych.com.routineplanner.domain.repository.templates

import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.usecase.UseCase
import androidx.lifecycle.LiveData

class GetTemplatesWithFullDoingsUseCase(
    private val templatesRepository: TemplatesRepository
) : UseCase<Unit, LiveData<List<Template>>>(){

    override fun execute(parameters: Unit): LiveData<List<Template>> {
        return templatesRepository.getTemplatesWithFullDoings()
    }
}