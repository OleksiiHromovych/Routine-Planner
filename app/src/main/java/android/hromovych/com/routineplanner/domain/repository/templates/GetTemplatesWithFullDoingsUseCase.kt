package android.hromovych.com.routineplanner.domain.repository.templates

import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.usecase.UseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.map

class GetTemplatesWithFullDoingsUseCase(
    private val templatesRepository: TemplatesRepository
) : UseCase<Unit, LiveData<List<Template>>>(){

    override fun execute(parameters: Unit): LiveData<List<Template>> {
        return templatesRepository.getTemplatesWithFullDoings().map {
            it.map { template ->
                template.apply {
                    doings = doings.sortedBy { it.position }
                }
            }
        }
    }
}