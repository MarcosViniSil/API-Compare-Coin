package com.investor.api.dtos

import com.investor.api.entities.Email
import jakarta.persistence.ElementCollection

class HistoricEmailsDTO(@ElementCollection var emails: List<Email> = mutableListOf()) {
}