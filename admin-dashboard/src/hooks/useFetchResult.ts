import { useState, useEffect } from 'react';

type Result<T> = { data: T | null; error: string | null; loading: boolean };

export function useFetchResult<T>(url: string): Result<T> {
  const [state, setState] = useState<Result<T>>({ data: null, error: null, loading: true });

  useEffect(() => {
    fetch(url)
      .then(res => res.ok ? res.json() : Promise.reject(res.statusText))
      .then(data => setState({ data, error: null, loading: false }))
      .catch(err => setState({ data: null, error: String(err), loading: false }));
  }, [url]);

  return state;
}
