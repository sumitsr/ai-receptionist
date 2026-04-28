variable "project_id" {
  description = "GCP Project ID"
  type        = string
}

variable "region" {
  description = "GCP Region for Cloud Run and Firestore"
  type        = string
  default     = "europe-west2"
}
