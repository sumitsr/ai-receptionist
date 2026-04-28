provider "google" {
  project = var.project_id
  region  = var.region
}

# Cloud Storage for Call Recordings
resource "google_storage_bucket" "call_recordings" {
  name          = "${var.project_id}-recordings"
  location      = var.region
  force_destroy = false
  
  lifecycle_rule {
    condition {
      age = 30
    }
    action {
      type          = "SetStorageClass"
      storage_class = "NEARLINE"
    }
  }
}

# Firestore Native Database
resource "google_firestore_database" "database" {
  project     = var.project_id
  name        = "(default)"
  location_id = var.region
  type        = "FIRESTORE_NATIVE"
}

# Voice Gateway Cloud Run Service
resource "google_cloud_run_v2_service" "voice_gateway" {
  name     = "voice-gateway"
  location = var.region

  template {
    containers {
      image = "gcr.io/${var.project_id}/voice-gateway:latest"
      resources {
        limits = {
          cpu    = "2"
          memory = "1024Mi"
        }
      }
    }
    scaling {
      min_instance_count = 1 # Keep warm for <800ms latency
      max_instance_count = 10
    }
  }
}
