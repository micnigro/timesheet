package com.perigea.tracker.timesheet.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.UtenteException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.AnagraficaDipendenteRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;


@Service
public class DipendenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private AnagraficaDipendenteRepository dipendenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	// metodo per creare un dipendente
	public AnagraficaDipendente createDipendente(AnagraficaDipendenteInputDto dipendenteDto) {
		try {
			Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(dipendenteDto.getUtenteDto());
			AnagraficaDipendente dipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteDto);
			dipendente.setUtenteDipendente(utente);
			utente.setDipendente(dipendente);
			Utente responsabile = utenteRepository.findByCodicePersona(dipendenteDto.getUtenteDto().getCodicePersona());
			utente.setResponsabile(responsabile);
			if(responsabile != null) {
				responsabile.addDipendente(utente);
			}
			utenteRepository.save(utente);
			dipendente.setCodicePersona(utente.getCodicePersona());
			return dipendente;
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}


	// Metodo per leggere i dati di un determinato dipendente
	public AnagraficaDipendente readDipendente(String dipendenteParam) {
		try {
			return dipendenteRepository.findByCodicePersona(dipendenteParam);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare i dati di un dipendente
	public AnagraficaDipendente updateDipendente(AnagraficaDipendenteInputDto dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam.getUtenteDto().getCodicePersona());
			if (anagDipendente != null) {
				anagDipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteParam);
				dipendenteRepository.save(anagDipendente);
			}
			
//			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
//			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
//			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			anagDipendente.setUtenteDipendente(anagDipendente.getUtenteDipendente());
//			return anagraficaResponseDto;
			return anagDipendente;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per eliminare un dipendente da database
	public AnagraficaDipendente deleteDipendente(String id) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(id);
			if (anagDipendente != null) {
				utenteRepository.delete(anagDipendente.getUtenteDipendente());
				dipendenteRepository.delete(anagDipendente);
			}
//			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
//			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
//			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			anagDipendente.setUtenteDipendente(anagDipendente.getUtenteDipendente());
//			return anagraficaResponseDto;
			return anagDipendente;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
	
	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
		public Utente editStatusUser(UtentePostDto utenteDto) {
			try {
				Utente entity = utenteRepository.findByCodicePersona(utenteDto.getCodicePersona());
				if (entity != null) {
					entity.setStatoUtenteType(utenteDto.getStatoUtenteType());
					entity.setLastUpdateUser("");
					utenteRepository.save(entity);
				}
				Utente responsabile = entity.getResponsabile();
//				UtenteViewDto respDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
//				UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(entity);
//				dto.setResponsabileDto(respDto);
				entity.setResponsabile(responsabile);
//				return dto;
				return entity;
			} catch (Exception ex) {
				throw new EntityNotFoundException(ex.getMessage());
			}
		}
		
		public void editRoleUser(RuoloDto roleParam, UtentePostDto userParam) {
			// if(mapEditUser.containsKey(key)) {
			// List<UtenteEntity>
			// entity=userRepo.findByRuoloType(roleParam.getRuoloType().toString());
			// for(UtenteEntity u: entity) {
			// if(u.getCodicePersona().equalsIgnoreCase(userParam.getCodicePersona())) {
			// u.setStatoUtenteType(userParam.getStatoUtenteType().toString());
			// }
			// }
			// } else {
			// LOGGER.info("CreateUser non trovato");
			// }
		}

}