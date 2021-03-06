package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaNonFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.CommessaService;



@RestController
@RequestMapping("/commesse")
public class CommessaController {

	@Autowired
	private CommessaService commessaService;

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> createCommessaFatturabile(@RequestBody CommessaFatturabileDtoWrapper commessaParam) {
//		CommessaFatturabileDto dto = commessaService.createCommessaFatturabile(commessaParam);
		CommessaFatturabile commessaEntity = commessaService.createCommessaFatturabile(commessaParam);
		CommessaFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(commessaEntity);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> createCommessaNonFatturabile(@RequestBody CommessaNonFatturabileDtoWrapper body) {
//		CommessaNonFatturabileDto dto = commessaService.createCommessaNonFatturabile(body.getCommessaNonFatturabileDto());
		CommessaNonFatturabile commessaEntity = commessaService.createCommessaNonFatturabile(body.getCommessaNonFatturabileDto());
		CommessaNonFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessaEntity);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	// Metodo per creare un ordine commessa
		@PostMapping(value = "/create-ordine-commessa")
		public ResponseEntity<GenericWrapperResponse<OrdineCommessaDto>> createOrdineCommessa(@RequestBody CommessaFatturabileDtoWrapper body,  @RequestParam String ragioneSocialeCliente) {
//			OrdineCommessaDto dto = commessaService.createOrdineCommessa(body, ragioneSocialeCliente);
			OrdineCommessa ordineCommessaEntity = commessaService.createOrdineCommessa(body, ragioneSocialeCliente);
			OrdineCommessaDto ordineCommessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoOrdineCommessa(ordineCommessaEntity);
			GenericWrapperResponse<OrdineCommessaDto> genericDto = GenericWrapperResponse.<OrdineCommessaDto>builder()
					.dataRichiesta(new Date()).risultato(ordineCommessaDto).build();
			return ResponseEntity.ok(genericDto);
		}

	// Metodo per creare un timesheet
	@GetMapping(value = "/delete-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> deleteCommessaNonFatturabile(@RequestParam String codiceCommessa) {
//		CommessaNonFatturabileDto dto = commessaService.deleteCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabile commessaEntity = commessaService.deleteCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessaEntity);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

}
